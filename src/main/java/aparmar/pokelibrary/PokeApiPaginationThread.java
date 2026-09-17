package aparmar.pokelibrary;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

import org.jetbrains.annotations.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.APIResourceList;
import aparmar.pokelibrary.objects.utility.AbstractApiResourceList;
import aparmar.pokelibrary.objects.utility.NamedAPIResourceList;
import aparmar.pokelibrary.utils.HelperConstants;
import lombok.Getter;
import lombok.extern.java.Log;
import okhttp3.ResponseBody;

@Log
public class PokeApiPaginationThread<T extends PkmnDataObject & IPaginatedDataObject> extends Thread {
	public static final double DEFAULT_FILL_FACTOR = 1.5;
	public static final int DEFAULT_QUEUE_THRESHOLD = 50;
	public static final int MAX_THREAD_ERRORS = 5;
	
	private final PokeApiLibrary apiLibrary;
	private final Gson gson;
	private final String apiEndpoint;
	private final Class<T> clazz;
	private final int paginationSize;
	private final boolean useItemCache;
	private final PaginationCacheUsage paginationCacheUsage;
	
	private int loadedItems = 0;
	private Integer knownItemCount = null;
	
	@Getter
	private final ConcurrentLinkedQueue<T> itemQueue;
	private final Queue<T> itemPreQueue = new LinkedList<>();
	@Getter
	private final int queueThreshold;
	
	@Getter
	private volatile boolean closed = false;
	@Getter
	private volatile boolean finished = false;
	private int errorCount = 0;
	private WeakReference<Object> consumerRef;
	
	public static int calculateDefaultThreshold(int paginationSize) {
		if (paginationSize <= 0) {
			return DEFAULT_QUEUE_THRESHOLD;
		}
		int calculated = (int) Math.ceil(paginationSize * DEFAULT_FILL_FACTOR);
		if (calculated <= 0) {
			return DEFAULT_QUEUE_THRESHOLD;
		}
		return Math.min(DEFAULT_QUEUE_THRESHOLD, calculated);
	}
	
	public PokeApiPaginationThread(PokeApiLibrary apiLibrary, Gson gson, String apiEndpoint, Class<T> clazz,
			int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage,
			ConcurrentLinkedQueue<T> itemQueue, int queueThreshold) {
		super();
		this.setDaemon(true);
		this.apiLibrary = apiLibrary;
		this.gson = gson;
		this.apiEndpoint = apiEndpoint;
		this.clazz = clazz;
		this.paginationSize = paginationSize;
		this.useItemCache = useItemCache;
		this.paginationCacheUsage = paginationCacheUsage;
		this.itemQueue = itemQueue;
		this.queueThreshold = queueThreshold;
	}
	
	public PokeApiPaginationThread(PokeApiLibrary apiLibrary, Gson gson, String apiEndpoint, Class<T> clazz,
			int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage,
			ConcurrentLinkedQueue<T> itemQueue) {
		this(apiLibrary, gson, apiEndpoint, clazz, paginationSize, useItemCache, paginationCacheUsage, itemQueue, calculateDefaultThreshold(paginationSize));
	}
	
	public void setConsumerReference(Object consumer) {
		this.consumerRef = new WeakReference<>(consumer);
	}
	
	public void close() {
		this.closed = true;
		this.interrupt();
	}
	
	private boolean shouldTerminate() {
		if (closed || Thread.currentThread().isInterrupted() || errorCount > MAX_THREAD_ERRORS) {
			return true;
		}
		if (consumerRef != null && consumerRef.get() == null) {
			close();
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			knownItemCount = (paginationCacheUsage == PaginationCacheUsage.USE_CACHE)
					? apiLibrary.getDataCache().getCachedPaginationCount(clazz)
					: null;
			Type resourceListType = PkmnNamedDataObject.class.isAssignableFrom(clazz)
					? TypeToken.getParameterized(NamedAPIResourceList.class, clazz).getType()
					: TypeToken.getParameterized(APIResourceList.class, clazz).getType();
			
			while (!shouldTerminate() && (knownItemCount == null || loadedItems < knownItemCount)) {
				if (!itemQueue.isEmpty()) {
					LockSupport.parkNanos(100_000_000L);
					continue;
				}
				
				int queuedItems = 0;
				while (!itemPreQueue.isEmpty() && queuedItems < queueThreshold) {
					itemQueue.offer(itemPreQueue.poll());
					queuedItems++;
				}
				boolean mightHaveMore = true;
				while (queuedItems < queueThreshold && mightHaveMore) {
					if (shouldTerminate()) {
						return;
					}
					
					mightHaveMore &= refilPreQueue(resourceListType);
					if (shouldTerminate()) {
						return;
					}
					
					while (!itemPreQueue.isEmpty() && queuedItems < queueThreshold) {
						itemQueue.offer(itemPreQueue.poll());
						queuedItems++;
					}
				}
			}
		} finally {
			finished = true;
		}
	}
	
	private boolean refilPreQueue(Type resourceListType) {
		if (knownItemCount != null && loadedItems >= knownItemCount) {
			return false;
		}
		
		if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
			APIResource<T> cachedResult = apiLibrary.getDataCache().getCachedPaginationResult(clazz, loadedItems, true);
			if (cachedResult != null) {
				T item = tryLoadResource(cachedResult);
				if (item != null) {
					itemPreQueue.offer(item);
					loadedItems++;
					return true;
				}
			}
		}
		
		String paginationUrl = HelperConstants.POKE_API_BASE_URL + "/" + apiEndpoint + "?limit=" + paginationSize + "&offset=" + loadedItems;
		
		try {
			AbstractApiResourceList<T, ? extends APIResource<T>> paginationList = apiLibrary.sendRequest(paginationUrl, (ResponseBody body) -> {
				return gson.fromJson(body.string(), resourceListType);
			});
			
			if (paginationList == null) {
				errorCount++;
				return true;
			}

			if (paginationList.getResults() == null || paginationList.getResults().isEmpty()) {
				knownItemCount = loadedItems;
				return false;
			}
			
			knownItemCount = paginationList.getCount();
			if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
				apiLibrary.getDataCache().updateCachedPaginationCount(clazz, knownItemCount);
			}
			
			for (APIResource<T> result : paginationList.getResults()) {
				if (shouldTerminate()) {
					return false;
				}
				
				if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
					apiLibrary.getDataCache().updateCachedPaginationResult(clazz, loadedItems, result);
				}
				
				T item = tryLoadResource(result);
				if (item != null) {
					itemPreQueue.offer(item);
				}
				loadedItems++;
			}
			return true;
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			if (!closed) {
				log.warning(String.format("Error fetching paginated data from '%s': %s", paginationUrl, e.getMessage()));
				errorCount++;
			}
			return true;
		}
	}
	
	@Nullable
	private T tryLoadResource(APIResource<T> resource) {
		if (resource == null || shouldTerminate()) { return null; }
		
		T item = null;
		try {
			item = apiLibrary.getResource(resource, !useItemCache);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			if (!closed) {
				log.warning(String.format("Error fetching resource data from '%s': %s", resource.getUrl(), e.getMessage()));
			}
		}
		return item;
	}
	
}

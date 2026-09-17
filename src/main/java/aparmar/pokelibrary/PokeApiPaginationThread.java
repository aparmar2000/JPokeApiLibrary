package aparmar.pokelibrary;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import okhttp3.ResponseBody;

@Log
@RequiredArgsConstructor
public class PokeApiPaginationThread<T extends PkmnDataObject & IPaginatedDataObject> extends Thread {
	private final PokeApiLibrary apiLibrary;
	private final Gson gson;
	private final String apiEndpoint;
	private final Class<T> clazz;
	private final int paginationSize;
	private final boolean useItemCache;
	private final PaginationCacheUsage paginationCacheUsage;
	
	private final ConcurrentLinkedQueue<T> itemQueue;
	
	@Override
	public void run() {
		Integer knownItemCount = (paginationCacheUsage == PaginationCacheUsage.USE_CACHE)
				? apiLibrary.getDataCache().getCachedPaginationCount(clazz)
				: null;
		int loadedItems = 0;
		Type resourceListType = PkmnNamedDataObject.class.isAssignableFrom(clazz)
				? TypeToken.getParameterized(NamedAPIResourceList.class, clazz).getType()
				: TypeToken.getParameterized(APIResourceList.class, clazz).getType();
		
		while (!Thread.currentThread().isInterrupted() && (knownItemCount == null || loadedItems < knownItemCount)) {
			if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
				APIResource<T> cachedResult = apiLibrary.getDataCache().getCachedPaginationResult(clazz, loadedItems, true);
				if (cachedResult != null) {
					T item = tryLoadResource(cachedResult);
					
					if (item != null) {
						itemQueue.offer(item);
						loadedItems++;
						continue;
					}
				}
			}
			
			String paginationUrl = HelperConstants.POKE_API_BASE_URL + "/" + apiEndpoint + "?limit=" + paginationSize + "&offset=" + loadedItems;
			
			try {
				AbstractApiResourceList<T, ? extends APIResource<T>> paginationList = apiLibrary.sendRequest(paginationUrl, (ResponseBody body) -> {
					return gson.fromJson(body.string(), resourceListType);
				});
				
				if (paginationList == null || paginationList.getResults() == null || paginationList.getResults().isEmpty()) {
					break;
				}
				
				knownItemCount = paginationList.getCount();
				if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
					apiLibrary.getDataCache().updateCachedPaginationCount(clazz, knownItemCount);
				}
				
				for (APIResource<T> result : paginationList.getResults()) {
					if (paginationCacheUsage != PaginationCacheUsage.DISABLE_CACHE) {
						apiLibrary.getDataCache().updateCachedPaginationResult(clazz, loadedItems, result);
					}
					
					T item = tryLoadResource(result);
					if (item != null) {
						itemQueue.offer(item);
					}
					loadedItems++;
				}
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				log.warning(String.format("Error fetching paginated data from '%s': %s", paginationUrl, e.getMessage()));
				break;
			}
		}
	}
	
	@Nullable
	private T tryLoadResource(APIResource<T> resource) {
		if (resource == null) { return null; }
		
		T item = null;
		try {
			item = apiLibrary.getResource(resource, !useItemCache);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			log.warning(String.format("Error fetching resource data from '%s': %s", resource.getUrl(), e.getMessage()));
		}
		return item;
	}
	
}

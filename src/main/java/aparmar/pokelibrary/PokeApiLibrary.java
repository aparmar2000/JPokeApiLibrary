package aparmar.pokelibrary;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import aparmar.pokelibrary.apidatahelpers.PkmnDataProvider;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.APIResourceList;
import aparmar.pokelibrary.objects.utility.NamedAPIResourceList;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import aparmar.pokelibrary.utils.GsonProvider;
import aparmar.pokelibrary.utils.RateLimitInterceptor;
import aparmar.pokelibrary.utils.ResultParseFunction;
import aparmar.pokelibrary.utils.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Log
public class PokeApiLibrary {
	private static final RateLimitInterceptor sharedRateLimiter = new RateLimitInterceptor(100);
	
	private final Gson gson;
	private final OkHttpClient client;
	@SuppressWarnings("rawtypes")
	private final LoadingCache<APIResource, Object> memCache;
	private final LoadingCache<String, PokeApiUrl> urlCache;
	
	private final LoadingCache<Class<IEnumerablePkmnData>, PokeDataIndex<IEnumerablePkmnData>> indexCache;
	@SuppressWarnings("rawtypes")
	private final Cache<Class<PkmnDataProvider>, PkmnDataProvider> dataProviderCache;
	
	public PokeApiLibrary(File cacheFolder, int concurrencyLevel, boolean weak) {
		client = buildHttpClient(Duration.ofSeconds(5));
		gson = GsonProvider.buildGsonInstance(this);
		
		val cacheBuilder = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel);
		if (weak) {
			cacheBuilder.weakValues();
		} else {
			cacheBuilder.softValues();
		}
		memCache = cacheBuilder.build(new PokeApiResourceLoader(gson, this, cacheFolder));
		
		urlCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.weakValues()
				.build(CacheLoader.from(PokeApiUrl::fromUrlString));
		
		indexCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.weakValues()
				.build(CacheLoader.from(dataClazz->new PokeDataIndex<IEnumerablePkmnData>(this, dataClazz)));
		
		dataProviderCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.build();
	}
	
	private OkHttpClient buildHttpClient(Duration readTimeout) {
		return new OkHttpClient.Builder()
			    .addNetworkInterceptor(sharedRateLimiter)
			    .readTimeout(readTimeout)
			    .build();
	}
	
	@RequiredArgsConstructor
	@SuppressWarnings("rawtypes")
	private static class PokeApiResourceLoader extends CacheLoader<APIResource, Object> {
		private final Gson gson;
		private final PokeApiLibrary apiLibrary;
		private final File cacheFolder;

		@SuppressWarnings("unchecked")
		@Override
		public Object load(APIResource resource) throws Exception {
			val resourceUrl = resource.getUrl();
			val cacheFile = cacheFolder.toPath()
					.resolve(Path.of(resourceUrl.getRelativeUrl()+".json"))
					.toFile();
			
			boolean fileCacheAllowed = true;
			if (APIResourceList.class.isAssignableFrom(resource.getClazz()) || NamedAPIResourceList.class.isAssignableFrom(resource.getClazz())) {
				fileCacheAllowed = false;
			}
			
			// Attempt to read from file cache
			if (fileCacheAllowed && cacheFile.canRead()) {
				try (FileReader in = new FileReader(cacheFile)) {
					return gson.fromJson(in, resource.getClazz());
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeApiLibrary.log.warning(String.format("Failed to read cache file at '%s' - falling back to web.", cacheFile));
					PokeApiLibrary.log.warning(e.getLocalizedMessage());
				}
			}
			
			// Fetch from web
			Object fetched = getResourceFromWeb(resource);
			
			// Update file cache
			if (fileCacheAllowed) {
				Files.createParentDirs(cacheFile);
				try (FileWriter out = new FileWriter(cacheFile)) {
					gson.toJson(fetched, out);
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeApiLibrary.log.severe(String.format("Failed to write cache file at '%s'.", cacheFile));
					PokeApiLibrary.log.severe(e.getLocalizedMessage());
				}
			}
			
			return fetched;
		}
		
		@SuppressWarnings("unchecked")
		private Object getResourceFromWeb(APIResource resource) throws JsonSyntaxException, JsonIOException, IOException {
			return apiLibrary.sendRequest(resource.getUrl().getUrl(), (ResponseBody body)->gson.fromJson(body.string(), resource.getClazz()));
		}
	}
	
	public PokeApiUrl getPokeApiUrlFromString(String url) throws Throwable {
		try {
			return urlCache.get(url);
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getResource(APIResource<T> resource) throws Throwable {
		try {
			return (T) memCache.get(resource);
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}
	
	public <T extends IPaginatedDataObject> Iterator<T> getPaginatedResourceIterator(Class<T> resourceClazz, int paginationSize) {
		val apiPathAnnotation = resourceClazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new NullPointerException(String.format("Class %s is missing required @ApiPath annotation!", resourceClazz));
		}
		String apiEndpoint = apiPathAnnotation.value();
		
		return new PokeApiPaginationIterator<T>(this, gson, apiEndpoint, resourceClazz, paginationSize);
	}
	public <T extends IPaginatedDataObject> Stream<T> getPaginatedResourceStream(Class<T> resourceClazz, int paginationSize) {
		return StreamSupport.stream(
		          Spliterators.spliteratorUnknownSize(getPaginatedResourceIterator(resourceClazz, paginationSize), Spliterator.ORDERED),
		          false);
	}
	public <T extends IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz, int paginationSize, int limit) {
		return getPaginatedResourceStream(resourceClazz, paginationSize)
				.limit(limit)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	public <T extends IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz, int paginationSize) {
		return getPaginatedResourceStream(resourceClazz, paginationSize)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public <T extends IPaginatedDataObject> Iterator<T> getPaginatedResourceIterator(Class<T> resourceClazz) {
		val apiPathAnnotation = resourceClazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new NullPointerException(String.format("Class %s is missing required @ApiPath annotation!", resourceClazz));
		}
		String apiEndpoint = apiPathAnnotation.value();
		
		return new PokeApiPaginationIterator<T>(this, gson, apiEndpoint, resourceClazz, 10000);
	}
	public <T extends IPaginatedDataObject> Stream<T> getPaginatedResourceStream(Class<T> resourceClazz) {
		return StreamSupport.stream(
		          Spliterators.spliteratorUnknownSize(getPaginatedResourceIterator(resourceClazz, 10000), Spliterator.ORDERED),
		          false);
	}
	public <T extends IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz) {
		return getPaginatedResourceStream(resourceClazz, 10000)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IEnumerablePkmnData> PokeDataIndex<T> getEnumerableIndex(Class<T> resourceClazz) {
		try {
			return (PokeDataIndex<T>) indexCache.get((Class<IEnumerablePkmnData>) resourceClazz);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// -----
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K extends PkmnDataProvider.INamedEnum, V extends IPaginatedDataObject, T extends PkmnDataProvider<K, V>> T getDataProvider(Class<T> clazz, Function<PokeApiLibrary, T> constructor) {
		try {
			return (T) dataProviderCache.get((Class<PkmnDataProvider>) clazz, ()->constructor.apply(this));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// -----
	<T> T sendRequest(String url, ResultParseFunction<T> deserializer) throws IOException, JsonSyntaxException, JsonIOException {
		Request request = new Request.Builder()
				.url(url.contains("https") ? url : "https://" + url)
				.get()
				.build();
		return executeAndParseRequest(deserializer, request);
	}

	<T> T executeAndParseRequest(ResultParseFunction<T> deserializer, Request request) throws IOException, TooManyRequestsException {
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				if (response.code()==429) {
					throw new TooManyRequestsException(response);
				} else {
					throw new IOException("Recieved " + response.code()+": "+response.body().string());
				}
			}
			
			return deserializer.apply(response.body());
		}
	}
}

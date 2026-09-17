package aparmar.pokelibrary;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import aparmar.pokelibrary.PokeDataCache.APIResourceRequest;
import aparmar.pokelibrary.apidatahelpers.PkmnDataProvider;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import aparmar.pokelibrary.utils.GsonProvider;
import aparmar.pokelibrary.utils.RateLimitInterceptor;
import aparmar.pokelibrary.utils.ResultParseFunction;
import aparmar.pokelibrary.utils.TooManyRequestsException;
import lombok.Getter;
import lombok.val;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Log
public class PokeApiLibrary {
	private static final RateLimitInterceptor sharedRateLimiter = new RateLimitInterceptor(50);
	
	@Getter
	private final Gson gson;
	private final OkHttpClient client;
	@Getter
	private final PokeDataCache dataCache;
	
	public PokeApiLibrary(File cacheFolder, int concurrencyLevel, boolean weak) {
		client = buildHttpClient(Duration.ofSeconds(5));
		gson = GsonProvider.buildGsonInstance(this);
		dataCache = new PokeDataCache(this, cacheFolder, concurrencyLevel, weak);
	}
	
	public PokeApiLibrary(PokeDataCache dataCache) {
		client = buildHttpClient(Duration.ofSeconds(5));
		gson = GsonProvider.buildGsonInstance(this);
		this.dataCache = dataCache;
	}
	
	private OkHttpClient buildHttpClient(Duration readTimeout) {
		return new OkHttpClient.Builder()
			    .addNetworkInterceptor(sharedRateLimiter)
			    .readTimeout(readTimeout)
			    .build();
	}
	
	public PokeApiUrl getPokeApiUrlFromString(String url) throws Throwable {
		return dataCache.getPokeApiUrlFromString(url);
	}
	
	public <T extends PkmnDataObject> T getResourceById(Class<T> clazz, int id) throws JsonSyntaxException, JsonIOException, IOException {
		return getResource(getResourceReferenceByClassAndId(clazz, id));
	}
	public <T extends PkmnNamedDataObject> T getResourceByName(Class<T> clazz, String name) throws JsonSyntaxException, JsonIOException, IOException {
		return getResource(getResourceReferenceByClassAndName(clazz, name));
	}
	
	public <T extends PkmnDataObject> APIResource<T> getResourceReferenceByClassAndId(Class<T> clazz, int id) {
		val apiResource = new APIResource<T>();
		
		apiResource.setUrl(PokeApiUrl.fromClassAndId(clazz, id));
		apiResource.setLibInstance(this);
		apiResource.setClazz(clazz);
		
		return apiResource;
	}
	public <T extends PkmnNamedDataObject> APIResource<T> getResourceReferenceByClassAndName(Class<T> clazz, String name) {
		val apiResource = new APIResource<T>();
		
		apiResource.setUrl(PokeApiUrl.fromClassAndName(clazz, name));
		apiResource.setLibInstance(this);
		apiResource.setClazz(clazz);
		
		return apiResource;
	}
	
	// ---

	public <T extends PkmnDataObject> T getResource(APIResourceRequest<T,?> resourceRequest) throws JsonSyntaxException, JsonIOException, IOException {
		return dataCache.getResource(resourceRequest);
	}
	public <T extends PkmnDataObject> T getResource(APIResource<T> resource) throws JsonSyntaxException, JsonIOException, IOException {
		return dataCache.getResource(resource);
	}
	public <T extends PkmnDataObject> T getResource(APIResource<T> resource, boolean disableCache) throws JsonSyntaxException, JsonIOException, IOException {
		return dataCache.getResource(resource, disableCache);
	}
	
	// ---
	
	public <T extends PkmnDataObject & IPaginatedDataObject> CloseableIterator<T> getPaginatedResourceIterator(Class<T> resourceClazz, int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		val apiPathAnnotation = resourceClazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new NullPointerException(String.format("Class %s is missing required @ApiPath annotation!", resourceClazz));
		}
		String apiEndpoint = apiPathAnnotation.value();
		
		return new PokeApiPaginationIterator<T>(this, gson, apiEndpoint, resourceClazz, paginationSize, useItemCache, paginationCacheUsage);
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> CloseableIterator<T> getPaginatedResourceIterator(Class<T> resourceClazz, int paginationSize, int queueThreshold, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		val apiPathAnnotation = resourceClazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new NullPointerException(String.format("Class %s is missing required @ApiPath annotation!", resourceClazz));
		}
		String apiEndpoint = apiPathAnnotation.value();
		
		return new PokeApiPaginationIterator<T>(this, gson, apiEndpoint, resourceClazz, paginationSize, useItemCache, paginationCacheUsage, queueThreshold);
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> Stream<T> getPaginatedResourceStream(Class<T> resourceClazz, int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		return getPaginatedResourceIterator(resourceClazz, paginationSize, useItemCache, paginationCacheUsage).stream();
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> Stream<T> getPaginatedResourceStream(Class<T> resourceClazz, int paginationSize, int queueThreshold, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		return getPaginatedResourceIterator(resourceClazz, paginationSize, queueThreshold, useItemCache, paginationCacheUsage).stream();
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz, int paginationSize, int limit, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		try (Stream<T> stream = getPaginatedResourceStream(resourceClazz, paginationSize, useItemCache, paginationCacheUsage)) {
			return stream
					.limit(limit)
					.collect(Collectors.toCollection(ArrayList::new));
		}
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz, int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		try (Stream<T> stream = getPaginatedResourceStream(resourceClazz, paginationSize, useItemCache, paginationCacheUsage)) {
			return stream
					.collect(Collectors.toCollection(ArrayList::new));
		}
	}
	
	public <T extends PkmnDataObject & IPaginatedDataObject> CloseableIterator<T> getPaginatedResourceIterator(Class<T> resourceClazz, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		return getPaginatedResourceIterator(resourceClazz, 10000, useItemCache, paginationCacheUsage);
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> Stream<T> getPaginatedResourceStream(Class<T> resourceClazz, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		return getPaginatedResourceStream(resourceClazz, 10000, useItemCache, paginationCacheUsage);
	}
	public <T extends PkmnDataObject & IPaginatedDataObject> List<T> getResourceList(Class<T> resourceClazz, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		return getResourceList(resourceClazz, 10000, useItemCache, paginationCacheUsage);
	}
	
	public <T extends PkmnDataObject & IEnumerablePkmnData> PokeDataIndex<T> getEnumerableIndex(Class<T> resourceClazz) {
		return dataCache.getEnumerableIndex(resourceClazz);
	}
	
	// -----
	
	public <K extends PkmnDataProvider.INamedEnum, V extends PkmnDataObject & IPaginatedDataObject, T extends PkmnDataProvider<K, V>> T getDataProvider(Class<T> clazz, Function<PokeApiLibrary, T> constructor) {
		return dataCache.getDataProvider(clazz, constructor);
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

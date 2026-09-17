package aparmar.pokelibrary;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import aparmar.pokelibrary.apidatahelpers.PkmnDataProvider;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.LoadSource;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import okhttp3.ResponseBody;

@Log
@Getter(value = AccessLevel.PACKAGE)
public class PokeDataCache {
	public static final String PAGINATION_CACHE_DIR_NAME = "pagination";

	private final PokeApiLibrary apiLibrary;
	@Getter
	@NonNull
	private final File cacheFolder;
	
	@SuppressWarnings("rawtypes")
	private final LoadingCache<APIResourceRequest, PkmnDataObject> memCache;
	@SuppressWarnings("rawtypes")
	private final Cache<APIResourcePaginationRequest, APIResource> memCachePagination;
	private final ConcurrentMap<Class<? extends PkmnDataObject>, Integer> memCachePaginationCount = new ConcurrentHashMap<>();
	private final LoadingCache<String, PokeApiUrl> urlCache;
	@SuppressWarnings("rawtypes")
	private final LoadingCache<Class<IEnumerablePkmnData>, PokeDataIndex> indexCache;
	@SuppressWarnings("rawtypes")
	private final Cache<Class<PkmnDataProvider>, PkmnDataProvider> dataProviderCache;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	PokeDataCache(PokeApiLibrary apiLibrary, File cacheFolder, int concurrencyLevel, boolean weak) {
		this.apiLibrary = apiLibrary;
		this.cacheFolder = cacheFolder;
		
		val cacheBuilder = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel);
		if (weak) {
			cacheBuilder.weakValues();
		} else {
			cacheBuilder.softValues();
		}
		memCache = cacheBuilder.build(new PokeApiResourceLoader(apiLibrary.getGson(), apiLibrary, cacheFolder));
		memCachePagination = cacheBuilder.build();
		
		urlCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.weakValues()
				.build(CacheLoader.from(PokeApiUrl::fromUrlString));
		
		indexCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.weakValues()
				.build(CacheLoader.from(dataClazz -> new PokeDataIndex(apiLibrary, dataClazz)));
		
		dataProviderCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.build();
	}

	@Value
	public static class APIResourceRequest<P extends PkmnDataObject, T extends APIResource<P>> {
		T wrapped;
		@Accessors(fluent = true)
		@EqualsAndHashCode.Exclude
		boolean allowFileCache;
		
		public PokeApiUrl getUrl() {
			return wrapped.getUrl();
		}
		public Class<P> getClazz() {
			return wrapped.getClazz();
		}
		
		public static <P extends PkmnDataObject, T extends APIResource<P>> APIResourceRequest<P, T> of(T wrapped) {
			return of(wrapped, true);
		}
		public static <P extends PkmnDataObject, T extends APIResource<P>> APIResourceRequest<P, T> of(T wrapped, boolean allowFileCache) {
			return new APIResourceRequest<P, T>(wrapped, allowFileCache);
		}
	}

	@Value
	public static class APIResourcePaginationRequest<T extends PkmnDataObject & IPaginatedDataObject> {
		Class<T> resourceClazz;
		int index;		
		@Accessors(fluent = true)
		@EqualsAndHashCode.Exclude
		boolean allowFileCache;

		public static <T extends PkmnDataObject & IPaginatedDataObject> APIResourcePaginationRequest<T> of(Class<T> resourceClazz, int index) {
			return of(resourceClazz, index, true);
		}
		public static <T extends PkmnDataObject & IPaginatedDataObject> APIResourcePaginationRequest<T> of(Class<T> resourceClazz, int index, boolean allowFileCache) {
			return new APIResourcePaginationRequest<>(resourceClazz, index, allowFileCache);
		}
	}

	@RequiredArgsConstructor
	@SuppressWarnings("rawtypes")
	private static class PokeApiResourceLoader extends CacheLoader<APIResourceRequest, PkmnDataObject> {
		private final Gson gson;
		private final PokeApiLibrary apiLibrary;
		private final File cacheFolder;

		@SuppressWarnings("unchecked")
		@Override
		public PkmnDataObject load(@NonNull APIResourceRequest resource) throws Exception {
			return loadInner(resource);
		}

		private <T extends PkmnDataObject> T loadInner(APIResourceRequest<T,?> resource) throws Exception {
			val resourceUrl = resource.getUrl();
			val cacheFile = cacheFolder != null
					? cacheFolder.toPath()
							.resolve(Path.of(resourceUrl.getRelativeUrl()+".json"))
							.toFile()
					: null;
			
			boolean fileCacheAllowed = cacheFile != null && resource.allowFileCache();
			
			// Attempt to read from file cache
			if (fileCacheAllowed && cacheFile != null && cacheFile.canRead()) {
				try (FileReader in = new FileReader(cacheFile)) {
					return PkmnDataObject.replicateWithNewSource(gson.fromJson(in, resource.getClazz()), LoadSource.FILE_CACHE, null);
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeDataCache.log.warning(String.format("Failed to read cache file at '%s' - falling back to web.", cacheFile));
					PokeDataCache.log.warning(e.getLocalizedMessage());
				}
			}
			
			// Fetch from web
			T fetched = getResourceFromWeb(apiLibrary, gson, resource);
			
			// Update file cache
			if (fileCacheAllowed) {
				Files.createParentDirs(cacheFile);
				try (FileWriter out = new FileWriter(cacheFile)) {
					gson.toJson(fetched, out);
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeDataCache.log.severe(String.format("Failed to write cache file at '%s'.", cacheFile));
					PokeDataCache.log.severe(e.getLocalizedMessage());
				}
			}
			
			return fetched;
		}
		
		static <T extends PkmnDataObject> T getResourceFromWeb(PokeApiLibrary apiLibrary, Gson gson, APIResourceRequest<T,?> resource) throws JsonSyntaxException, JsonIOException, IOException {
			T newObj = apiLibrary.sendRequest(resource.getUrl().getUrl(), (ResponseBody body)->gson.fromJson(body.string(), resource.getClazz()));
			newObj = PkmnDataObject.replicateWithNewSource(newObj, LoadSource.API, System.currentTimeMillis());
			return newObj;
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
	public <T extends PkmnDataObject> T getResource(APIResourceRequest<T,?> resourceRequest) throws JsonSyntaxException, JsonIOException, IOException {
		try {
			return (T) memCache.get(resourceRequest);
		} catch (ExecutionException e) {
			val cause = e.getCause();

			if (cause instanceof JsonSyntaxException) {
				throw (JsonSyntaxException) cause;
			}
			if (cause instanceof JsonIOException) {
				throw (JsonIOException) cause;
			}
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			
			throw new RuntimeException("LoadingCache encountered unforseen exception type during excecution!", cause);
		}
	}

	public <T extends PkmnDataObject> T getResource(APIResource<T> resource) throws JsonSyntaxException, JsonIOException, IOException {
		return getResource(APIResourceRequest.of(resource));
	}

	public <T extends PkmnDataObject> T getResource(APIResource<T> resource, boolean disableCache) throws JsonSyntaxException, JsonIOException, IOException {
		if (disableCache) {
			memCache.invalidate(APIResourceRequest.of(resource));
		}
		return getResource(APIResourceRequest.of(resource, !disableCache));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends PkmnDataObject & IEnumerablePkmnData> PokeDataIndex<T> getEnumerableIndex(Class<T> resourceClazz) {
		try {
			return (PokeDataIndex<T>) indexCache.get((Class<IEnumerablePkmnData>) (Class) resourceClazz);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <K extends PkmnDataProvider.INamedEnum, V extends PkmnDataObject & IPaginatedDataObject, T extends PkmnDataProvider<K, V>> T getDataProvider(Class<T> clazz, Function<PokeApiLibrary, T> constructor) {
		try {
			return (T) dataProviderCache.get((Class<PkmnDataProvider>) (Class) clazz, () -> constructor.apply(apiLibrary));
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// --
	
	File getPaginationCacheFile(Class<?> resourceClazz, int index) {
		String endpointPath = getEndpointPath(resourceClazz);
		return cacheFolder.toPath()
				.resolve(Path.of(PAGINATION_CACHE_DIR_NAME, endpointPath, index + ".json"))
				.toFile();
	}

	File getPaginationCountCacheFile(Class<?> resourceClazz) {
		String endpointPath = getEndpointPath(resourceClazz);
		return cacheFolder.toPath()
				.resolve(Path.of(PAGINATION_CACHE_DIR_NAME, endpointPath, "_count.json"))
				.toFile();
	}

	static String getEndpointPath(Class<?> clazz) {
		ApiPath apiPath = clazz.getAnnotation(ApiPath.class);
		if (apiPath != null) {
			if (apiPath.subEndpoint() != null && !apiPath.subEndpoint().isBlank()) {
				return apiPath.value() + "/" + apiPath.subEndpoint();
			}
			return apiPath.value();
		}
		return clazz.getSimpleName().toLowerCase();
	}
	
	// --

	@Nullable
	public <T extends PkmnDataObject & IPaginatedDataObject> APIResource<T> getCachedPaginationResult(Class<T> resourceClazz, int index) {
		return getCachedPaginationResult(resourceClazz, index, true);
	}

	@Nullable
	public <T extends PkmnDataObject & IPaginatedDataObject> APIResource<T> getCachedPaginationResult(Class<T> resourceClazz, int index, boolean allowFileCache) {
		return getCachedPaginationResult(APIResourcePaginationRequest.of(resourceClazz, index, allowFileCache));
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public <T extends PkmnDataObject & IPaginatedDataObject> APIResource<T> getCachedPaginationResult(APIResourcePaginationRequest<T> request) {
		APIResource<T> cached = (APIResource<T>) memCachePagination.getIfPresent(request);
		if (cached != null) {
			return cached;
		}
		
		if (cacheFolder == null || !request.allowFileCache()) {
			return null;
		}
		
		File cacheFile = getPaginationCacheFile(request.getResourceClazz(), request.getIndex());
		if (cacheFile != null && cacheFile.canRead()) {
			try (FileReader in = new FileReader(cacheFile)) {
				Type resourceType = PkmnNamedDataObject.class.isAssignableFrom(request.getResourceClazz())
						? TypeToken.getParameterized(NamedAPIResource.class, request.getResourceClazz()).getType()
						: TypeToken.getParameterized(APIResource.class, request.getResourceClazz()).getType();
				APIResource<T> loaded = apiLibrary.getGson().fromJson(in, resourceType);
				if (loaded != null) {
					loaded.setClazz(request.getResourceClazz());
					loaded.setLibInstance(apiLibrary);
					memCachePagination.put(request, loaded);
					return loaded;
				}
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				PokeDataCache.log.warning(String.format("Failed to read pagination cache file at '%s'.", cacheFile));
				PokeDataCache.log.warning(e.getLocalizedMessage());
			}
		}
		
		return null;
	}

	public <T extends PkmnDataObject & IPaginatedDataObject> void updateCachedPaginationResult(Class<T> resourceClazz, int index, APIResource<T> result) {
		updateCachedPaginationResult(APIResourcePaginationRequest.of(resourceClazz, index, true), result);
	}

	public <T extends PkmnDataObject & IPaginatedDataObject> void updateCachedPaginationResult(APIResourcePaginationRequest<T> request, APIResource<T> result) {
		if (result == null) {
			return;
		}
		memCachePagination.put(request, result);
		
		if (cacheFolder != null && request.allowFileCache()) {
			File cacheFile = getPaginationCacheFile(request.getResourceClazz(), request.getIndex());
			if (cacheFile != null) {
				try {
					Files.createParentDirs(cacheFile);
					try (FileWriter out = new FileWriter(cacheFile)) {
						apiLibrary.getGson().toJson(result, out);
					}
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeDataCache.log.severe(String.format("Failed to write pagination cache file at '%s'.", cacheFile));
					PokeDataCache.log.severe(e.getLocalizedMessage());
				}
			}
		}
	}

	@Nullable
	public Integer getCachedPaginationCount(Class<? extends PkmnDataObject> resourceClazz) {
		return getCachedPaginationCount(resourceClazz, true);
	}

	@Nullable
	public Integer getCachedPaginationCount(Class<? extends PkmnDataObject> resourceClazz, boolean allowFileCache) {
		Integer count = memCachePaginationCount.get(resourceClazz);
		if (count != null) {
			return count;
		}
		if (cacheFolder == null || !allowFileCache) {
			return null;
		}
		File countFile = getPaginationCountCacheFile(resourceClazz);
		if (countFile != null && countFile.canRead()) {
			try (FileReader in = new FileReader(countFile)) {
				Integer loaded = apiLibrary.getGson().fromJson(in, Integer.class);
				if (loaded != null) {
					memCachePaginationCount.put(resourceClazz, loaded);
					return loaded;
				}
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				PokeDataCache.log.warning(String.format("Failed to read pagination count file at '%s'.", countFile));
				PokeDataCache.log.warning(e.getLocalizedMessage());
			}
		}
		return null;
	}

	public void updateCachedPaginationCount(Class<? extends PkmnDataObject> resourceClazz, int count) {
		memCachePaginationCount.put(resourceClazz, count);
		if (cacheFolder != null) {
			File countFile = getPaginationCountCacheFile(resourceClazz);
			if (countFile != null) {
				try {
					Files.createParentDirs(countFile);
					try (FileWriter out = new FileWriter(countFile)) {
						apiLibrary.getGson().toJson(count, out);
					}
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeDataCache.log.severe(String.format("Failed to write pagination count file at '%s'.", countFile));
					PokeDataCache.log.severe(e.getLocalizedMessage());
				}
			}
		}
	}

	public void invalidatePagination() {
		memCachePagination.invalidateAll();
		invalidatePaginationCount();
	}

	public void invalidatePagination(Class<? extends PkmnDataObject> resourceClazz) {
		invalidatePaginationCount(resourceClazz);
		memCachePagination.asMap().keySet().removeIf(req -> {
			if (req instanceof APIResourcePaginationRequest) {
				return ((APIResourcePaginationRequest<?>) req).getResourceClazz().equals(resourceClazz);
			}
			return false;
		});
	}
	
	public void invalidatePaginationCount() {
		memCachePaginationCount.clear();
	}
	
	public void invalidatePaginationCount(Class<? extends PkmnDataObject> resourceClazz) {
		memCachePaginationCount.remove(resourceClazz);
	}

	public void invalidateResource(APIResource<?> resource) {
		memCache.invalidate(APIResourceRequest.of(resource));
	}

	public void invalidate(APIResourceRequest<?, ?> request) {
		memCache.invalidate(request);
	}

	public void invalidateAll() {
		memCache.invalidateAll();
		memCachePagination.invalidateAll();
		memCachePaginationCount.clear();
		urlCache.invalidateAll();
		indexCache.invalidateAll();
		dataProviderCache.invalidateAll();
	}
}

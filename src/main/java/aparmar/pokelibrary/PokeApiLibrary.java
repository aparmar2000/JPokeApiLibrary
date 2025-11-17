package main.java.aparmar.pokelibrary;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.java.Log;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.PokeApiUrl;
import main.java.aparmar.pokelibrary.utils.GsonProvider;
import main.java.aparmar.pokelibrary.utils.RateLimitInterceptor;
import main.java.aparmar.pokelibrary.utils.ResultParseFunction;
import main.java.aparmar.pokelibrary.utils.TooManyRequestsException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Log
public class PokeApiLibrary {
	private static final RateLimitInterceptor sharedRateLimiter = new RateLimitInterceptor(10);
	
	private final Gson gson;
	private final OkHttpClient client;
	@SuppressWarnings("rawtypes")
	private final LoadingCache<APIResource, Object> memCache;
	private final LoadingCache<String, PokeApiUrl> urlCache;
	
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
		memCache = cacheBuilder.build(new PokeApiResourceLoader(gson, client, cacheFolder));
		
		urlCache = CacheBuilder.newBuilder()
				.concurrencyLevel(concurrencyLevel)
				.weakValues()
				.build(CacheLoader.from(PokeApiUrl::fromUrlString));
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
		private final OkHttpClient client;
		private final File cacheFolder;

		@SuppressWarnings("unchecked")
		@Override
		public Object load(APIResource resource) throws Exception {
			val resourceUrl = resource.getUrl();
			val cacheFile = cacheFolder.toPath().resolve(Path.of(resourceUrl.getRelativeUrl(),".json")).toFile();
			
			// Attempt to read from file cache
			if (cacheFile.canRead()) {
				try (FileReader in = new FileReader(cacheFile)) {
					return gson.fromJson(in, resource.getClazz());
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeApiLibrary.log.warning(String.format("Failed to read cache file at '%s' - falling back to web.", cacheFile));
				}
			}
			
			// Fetch from web
			Object fetched = getResourceFromWeb(resource);
			
			// Update file cache
			if (cacheFile.canWrite()) {
				try (FileWriter out = new FileWriter(cacheFile)) {
					gson.toJson(fetched, out);
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					PokeApiLibrary.log.severe(String.format("Failed to write cache file at '%s'.", cacheFile));
				}
			} else {
				PokeApiLibrary.log.severe(String.format("Failed to write cache file at '%s'.", cacheFile));
			}
			
			return fetched;
		}
		
		@SuppressWarnings("unchecked")
		private Object getResourceFromWeb(APIResource resource) throws JsonSyntaxException, JsonIOException, IOException {
			return sendRequest(resource.getUrl().getUrl(), (ResponseBody body)->gson.fromJson(body.string(), resource.getClazz()));
		}
		
		private <T> T sendRequest(String host, ResultParseFunction<T> deserializer) throws IOException, JsonSyntaxException, JsonIOException {
			Request request = new Request.Builder()
					.url(new HttpUrl.Builder()
							.scheme("https")
							.host(host)
							.build())
					.get()
					.build();
			return executeAndParseRequest(deserializer, request);
		}

		private <T> T executeAndParseRequest(ResultParseFunction<T> deserializer, Request request) throws IOException, TooManyRequestsException {
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
}

package main.java.aparmar.pokelibrary;

import java.io.IOException;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.utils.GsonProvider;
import main.java.aparmar.pokelibrary.utils.RateLimitInterceptor;
import main.java.aparmar.pokelibrary.utils.ResultParseFunction;
import main.java.aparmar.pokelibrary.utils.TooManyRequestsException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PokeApiLibrary {
	private static final RateLimitInterceptor sharedRateLimiter = new RateLimitInterceptor(10);
	private final Gson gson;
	
	private final OkHttpClient client;
	
	public PokeApiLibrary() {
		client = buildHttpClient(Duration.ofSeconds(5));
		gson = GsonProvider.buildGsonInstance(this);
	}
	
	private OkHttpClient buildHttpClient(Duration readTimeout) {
		return new OkHttpClient.Builder()
			    .addNetworkInterceptor(sharedRateLimiter)
			    .readTimeout(readTimeout)
			    .build();
	}
	
	public <T> T getResourceByUrl(APIResource<T> resource, Class<T> clazz) throws JsonSyntaxException, JsonIOException, IOException {
		return sendRequest(resource.getUrl(), (ResponseBody body)->gson.fromJson(body.string(), clazz));
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

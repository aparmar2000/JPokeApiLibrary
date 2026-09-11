package aparmar.pokelibrary.utils.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;

class PokeApiUrlTypeAdapterTest {
	private PokeApiLibrary testLibrary;
	private PokeApiUrlTypeAdapter adapter;

	@BeforeEach
	void setUp() {
		testLibrary = new PokeApiLibrary(new File("target/test_cache"), 1, true);
		adapter = new PokeApiUrlTypeAdapter(testLibrary);
	}

	@Test
	@DisplayName("write correctly serializes PokeApiUrl to its URL string")
	void testWrite() throws IOException {
		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(stringWriter);
		PokeApiUrl url = PokeApiUrl.fromUrlString("https://pokeapi.co/api/v2/pokemon/25");

		adapter.write(jsonWriter, url);

		assertEquals("\"" + url.getUrl() + "\"", stringWriter.toString());
	}

	@Test
	@DisplayName("read parses string and delegates to PokeApiLibrary")
	void testReadParsesStringAndDelegates() throws IOException {
		String urlString = "https://pokeapi.co/api/v2/pokemon/25";
		PokeApiUrl expected = PokeApiUrl.fromUrlString(urlString);
		JsonReader jsonReader = new JsonReader(new StringReader("\"" + urlString + "\""));
		PokeApiUrl result = adapter.read(jsonReader);

		assertNotNull(result);
		assertEquals(expected, result);
		assertEquals("pokemon", result.getEndpointId());
		assertEquals("25", result.getId());
		assertNull(result.getSubEndpointId());
		assertEquals(expected.getUrl(), result.getUrl());
	}

	@Test
	@DisplayName("read throws JsonParseException when PokeApiLibrary fails to parse malformed URL")
	void testReadThrowsJsonParseExceptionOnFailure() {
		JsonReader jsonReader = new JsonReader(new StringReader("\"https://invalid-url.com\""));

		assertThrows(JsonParseException.class, () -> adapter.read(jsonReader));
	}

	@Test
	@DisplayName("read throws JsonParseException when PokeApiLibrary throws a checked Throwable")
	void testReadThrowsJsonParseExceptionOnCheckedException() {
		PokeApiLibrary throwingLibrary = new PokeApiLibrary(new File("target/test_cache"), 1, true) {
			@Override
			public PokeApiUrl getPokeApiUrlFromString(String url) throws Throwable {
				throw new Exception("Checked fetch failure");
			}
		};
		PokeApiUrlTypeAdapter throwingAdapter = new PokeApiUrlTypeAdapter(throwingLibrary);

		JsonReader jsonReader = new JsonReader(new StringReader("\"https://pokeapi.co/api/v2/pokemon/25\""));

		assertThrows(JsonParseException.class, () -> throwingAdapter.read(jsonReader));
	}

	@Test
	@DisplayName("Gson integration with nullSafe handles null and valid URLs")
	void testGsonIntegrationWithNullSafe() {
		PokeApiUrl expectedUrl = PokeApiUrl.fromUrlString("https://pokeapi.co/api/v2/pokemon/25");

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(PokeApiUrl.class, adapter.nullSafe())
				.create();

		// Test null serialization & deserialization
		assertEquals("null", gson.toJson(null, PokeApiUrl.class));
		assertNull(gson.fromJson("null", PokeApiUrl.class));

		// Test valid serialization & deserialization
		String serialized = gson.toJson(expectedUrl);
		assertEquals("\"" + expectedUrl.getUrl() + "\"", serialized);

		PokeApiUrl deserialized = gson.fromJson(serialized, PokeApiUrl.class);
		assertEquals(expectedUrl, deserialized);
	}
}

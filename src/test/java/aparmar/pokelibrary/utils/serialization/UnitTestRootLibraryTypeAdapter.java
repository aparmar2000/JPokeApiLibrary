package aparmar.pokelibrary.utils.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import aparmar.pokelibrary.PokeApiLibrary;

class UnitTestRootLibraryTypeAdapter {
	private PokeApiLibrary testLibrary;
	private RootLibraryTypeAdapter adapter;

	@BeforeEach
	void setUp() {
		testLibrary = new PokeApiLibrary(new File("target/test_cache"), 1, true);
		adapter = new RootLibraryTypeAdapter(testLibrary);
	}

	@Test
	@DisplayName("write does nothing to the JsonWriter")
	void testWriteDoesNothing() throws IOException {
		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(stringWriter);

		adapter.write(jsonWriter, testLibrary);

		assertEquals("", stringWriter.toString());
	}

	@Test
	@DisplayName("read returns the configured PokeApiLibrary instance from JsonReader")
	void testReadReturnsConfiguredInstance() throws IOException {
		JsonReader jsonReader = new JsonReader(new StringReader("{}"));

		PokeApiLibrary result = adapter.read(jsonReader);

		assertSame(testLibrary, result);
	}

	@Test
	@DisplayName("read returns the configured PokeApiLibrary instance regardless of reader content")
	void testReadReturnsConfiguredInstanceWithDifferentTokens() throws IOException {
		JsonReader jsonReader = new JsonReader(new StringReader("{\"key\": \"value\", \"nested\": [1, 2, 3]}"));

		PokeApiLibrary result = adapter.read(jsonReader);

		assertSame(testLibrary, result);
	}

	@Test
	@DisplayName("read returns the configured PokeApiLibrary instance when reader contains null")
	void testReadReturnsConfiguredInstanceWithNullToken() throws IOException {
		JsonReader jsonReader = new JsonReader(new StringReader("null"));

		PokeApiLibrary result = adapter.read(jsonReader);

		assertSame(testLibrary, result);
	}
}

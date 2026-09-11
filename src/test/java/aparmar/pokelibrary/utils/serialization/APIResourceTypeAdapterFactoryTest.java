package aparmar.pokelibrary.utils.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.pokemon.Pokemon;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;

class APIResourceTypeAdapterFactoryTest {
	private PokeApiLibrary testLibrary;
	private APIResourceTypeAdapterFactory factory;
	private Gson baseGson;

	@BeforeEach
	void setUp() {
		testLibrary = new PokeApiLibrary(new File("target/test_cache"), 1, true);
		factory = new APIResourceTypeAdapterFactory(testLibrary);
		baseGson = new Gson();
	}

	@Test
	@DisplayName("create returns null for types not assignable to APIResource")
	void testCreateReturnsNullForNonApiResource() {
		TypeAdapter<String> stringAdapter = factory.create(baseGson, TypeToken.get(String.class));
		assertNull(stringAdapter);

		TypeAdapter<Integer> intAdapter = factory.create(baseGson, TypeToken.get(Integer.class));
		assertNull(intAdapter);
	}

	@SuppressWarnings("rawtypes")
	@Test
	@DisplayName("create returns null for raw unparameterized APIResource")
	void testCreateReturnsNullForRawApiResource() {
		TypeAdapter<APIResource> rawAdapter = factory.create(baseGson, TypeToken.get(APIResource.class));
		assertNull(rawAdapter);
	}

	@Test
	@DisplayName("create returns null when generic type argument is wildcard")
	void testCreateReturnsNullForWildcardApiResource() {
		TypeToken<APIResource<?>> wildcardToken = new TypeToken<APIResource<?>>() {};
		TypeAdapter<APIResource<?>> adapter = factory.create(baseGson, wildcardToken);
		assertNull(adapter);
	}

	@Test
	@DisplayName("create returns non-null TypeAdapter for concrete parameterized APIResource")
	void testCreateReturnsAdapterForConcreteGenericType() {
		TypeToken<APIResource<Pokemon>> concreteToken = new TypeToken<APIResource<Pokemon>>() {};
		TypeAdapter<APIResource<Pokemon>> adapter = factory.create(baseGson, concreteToken);
		assertNotNull(adapter);
	}

	@Test
	@DisplayName("read populates clazz and libInstance on deserialized APIResource")
	void testReadPopulatesClazzAndLibInstance() {
		String urlString = "https://pokeapi.co/api/v2/pokemon/25";
		PokeApiUrl expectedUrl = PokeApiUrl.fromUrlString(urlString);

		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(factory)
				.registerTypeAdapter(PokeApiUrl.class, new PokeApiUrlTypeAdapter(testLibrary).nullSafe())
				.create();

		String json = "{\"url\": \"" + urlString + "\"}";
		TypeToken<APIResource<Pokemon>> token = new TypeToken<APIResource<Pokemon>>() {};
		APIResource<Pokemon> resource = gson.fromJson(json, token.getType());

		assertNotNull(resource);
		assertEquals(Pokemon.class, resource.getClazz());
		assertSame(testLibrary, resource.getLibInstance());
		assertEquals(expectedUrl, resource.getUrl());
	}

	@Test
	@DisplayName("write delegates serialization of APIResource")
	void testWriteDelegatesSerialization() {
		PokeApiUrl url = PokeApiUrl.fromUrlString("https://pokeapi.co/api/v2/pokemon/25");
		APIResource<Pokemon> resource = new APIResource<>();
		resource.setUrl(url);
		resource.setClazz(Pokemon.class);
		resource.setLibInstance(testLibrary);

		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(factory)
				.registerTypeAdapter(PokeApiUrl.class, new PokeApiUrlTypeAdapter(testLibrary).nullSafe())
				.create();

		String json = gson.toJson(resource, new TypeToken<APIResource<Pokemon>>() {}.getType());
		assertNotNull(json);
		assertEquals("{\"url\":\"" + url.getUrl() + "\"}", json);
	}

	@Test
	@DisplayName("null safe behavior handles null serialization and deserialization")
	void testNullSafeHandling() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(factory)
				.create();

		TypeToken<APIResource<Pokemon>> token = new TypeToken<APIResource<Pokemon>>() {};
		assertEquals("null", gson.toJson(null, token.getType()));
		assertNull(gson.fromJson("null", token.getType()));
	}
}

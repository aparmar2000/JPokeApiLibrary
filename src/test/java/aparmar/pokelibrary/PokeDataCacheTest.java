package aparmar.pokelibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aparmar.pokelibrary.PokeDataCache.APIResourcePaginationRequest;
import aparmar.pokelibrary.PokeDataCache.APIResourceRequest;
import aparmar.pokelibrary.objects.LoadSource;
import aparmar.pokelibrary.objects.berries.Berry;
import aparmar.pokelibrary.objects.pokemon.Pokemon;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;

class PokeDataCacheTest {
	private static final File TEST_CACHE = new File("target/test_cache_pokedatacache");
	private PokeApiLibrary apiLibrary;
	private PokeDataCache cache;

	@BeforeAll
	static void setUpAll() throws Exception {
		if (TEST_CACHE.exists()) {
			try (Stream<Path> walk = Files.walk(TEST_CACHE.toPath())) {
				walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
			}
		}
		Files.createDirectories(TEST_CACHE.toPath());
	}

	@BeforeEach
	void setUp() {
		apiLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
		cache = apiLibrary.getDataCache();
	}

	@Test
	void testCacheGettersAndLinkage() {
		assertNotNull(cache);
		assertSame(cache, apiLibrary.getDataCache());
		assertSame(apiLibrary, cache.getApiLibrary());
		assertEquals(TEST_CACHE, cache.getCacheFolder());
		assertNotNull(cache.getMemCache());
		assertNotNull(cache.getUrlCache());
		assertNotNull(cache.getIndexCache());
		assertNotNull(cache.getDataProviderCache());
	}

	@Test
	void testApiResourceRequestProperties() {
		APIResource<Berry> resource = apiLibrary.getResourceReferenceByClassAndId(Berry.class, 1);
		APIResourceRequest<Berry, APIResource<Berry>> req1 = APIResourceRequest.of(resource);
		assertTrue(req1.allowFileCache());
		assertEquals(resource.getUrl(), req1.getUrl());
		assertEquals(Berry.class, req1.getClazz());

		APIResourceRequest<Berry, APIResource<Berry>> req2 = APIResourceRequest.of(resource, false);
		assertFalse(req2.allowFileCache());
		assertEquals(req1, req2); // allowFileCache is excluded from equals/hashCode
	}

	@Test
	void testUrlCache() throws Throwable {
		String testUrl = "https://pokeapi.co/api/v2/berry/1/";
		PokeApiUrl url1 = cache.getPokeApiUrlFromString(testUrl);
		assertNotNull(url1);
		PokeApiUrl url2 = cache.getPokeApiUrlFromString(testUrl);
		assertSame(url1, url2);
	}

	@Test
	void testResourceCachingInMemoryAndDisk() throws IOException {
		APIResource<Berry> resource = apiLibrary.getResourceReferenceByClassAndId(Berry.class, 1);

		// First load - goes to API (or disk if exists)
		Berry berry1 = cache.getResource(resource);
		assertNotNull(berry1);
		assertEquals(1, berry1.getId());

		// In-memory cache returns identical instance
		Berry berry2 = cache.getResource(resource);
		assertSame(berry1, berry2);

		// Verify file written to cache folder
		File expectedCacheFile = TEST_CACHE.toPath()
				.resolve(Path.of(resource.getUrl().getRelativeUrl() + ".json"))
				.toFile();
		assertTrue(expectedCacheFile.exists(), "Cache file must exist: " + expectedCacheFile);
		assertTrue(expectedCacheFile.length() > 0, "Cache file must not be empty");

		// Invalidate memory cache and load from disk
		cache.invalidateResource(resource);
		Berry berryFromDisk = cache.getResource(resource);
		assertNotNull(berryFromDisk);
		assertEquals(berry1.getId(), berryFromDisk.getId());
		assertEquals(LoadSource.FILE_CACHE, berryFromDisk.getLoadSource());
	}

	@Test
	void testDisableCacheAndInvalidateAll() throws IOException {
		APIResource<Berry> resource = apiLibrary.getResourceReferenceByClassAndId(Berry.class, 2);

		Berry berry1 = cache.getResource(resource);
		assertNotNull(berry1);

		// disableCache causes a new request
		Berry berryNoCache = cache.getResource(resource, true);
		assertNotNull(berryNoCache);

		cache.invalidateAll();
		assertEquals(0, cache.getMemCache().size());
		assertEquals(0, cache.getMemCachePagination().size());
	}

	@Test
	void testPaginationRequestProperties() {
		APIResourcePaginationRequest<Pokemon> req1 = APIResourcePaginationRequest.of(Pokemon.class, 0);
		assertTrue(req1.allowFileCache());
		assertEquals(Pokemon.class, req1.getResourceClazz());
		assertEquals(0, req1.getIndex());

		APIResourcePaginationRequest<Pokemon> req2 = APIResourcePaginationRequest.of(Pokemon.class, 0, false);
		assertFalse(req2.allowFileCache());
		assertEquals(req1, req2); // allowFileCache excluded from equals/hashCode

		APIResourcePaginationRequest<Pokemon> req3 = APIResourcePaginationRequest.of(Pokemon.class, 1);
		assertNotEquals(req1, req3);
	}

	@Test
	void testPaginationInMemoryAndDiskCaching() {
		NamedAPIResource<Pokemon> pokemonResource = new NamedAPIResource<>();
		pokemonResource.setName("bulbasaur");
		pokemonResource.setUrl(PokeApiUrl.fromClassAndId(Pokemon.class, 1));
		pokemonResource.setClazz(Pokemon.class);
		pokemonResource.setLibInstance(apiLibrary);

		// Store in cache
		cache.updateCachedPaginationResult(Pokemon.class, 0, pokemonResource);

		// In-memory lookup returns identical instance
		APIResource<Pokemon> inMemory = cache.getCachedPaginationResult(Pokemon.class, 0);
		assertNotNull(inMemory);
		assertSame(pokemonResource, inMemory);

		// Verify disk file exists
		File expectedFile = cache.getPaginationCacheFile(Pokemon.class, 0);
		assertNotNull(expectedFile);
		assertTrue(expectedFile.exists(), "Pagination cache file must exist: " + expectedFile);
		assertTrue(expectedFile.length() > 0, "Pagination cache file must not be empty");

		// Clear memory cache and verify loading from disk
		cache.invalidatePagination();
		assertEquals(0, cache.getMemCachePagination().size());

		APIResource<Pokemon> fromDisk = cache.getCachedPaginationResult(Pokemon.class, 0);
		assertNotNull(fromDisk);
		assertEquals(pokemonResource.getUrl(), fromDisk.getUrl());
		assertEquals(Pokemon.class, fromDisk.getClazz());
		assertNotNull(fromDisk.getLibInstance());
		assertTrue(fromDisk instanceof NamedAPIResource);
		assertEquals("bulbasaur", ((NamedAPIResource<Pokemon>) fromDisk).getName());
	}

	@Test
	void testPaginationEndpointIsolation() {
		NamedAPIResource<Pokemon> pokemonResource = new NamedAPIResource<>();
		pokemonResource.setName("bulbasaur");
		pokemonResource.setUrl(PokeApiUrl.fromClassAndId(Pokemon.class, 1));
		pokemonResource.setClazz(Pokemon.class);
		pokemonResource.setLibInstance(apiLibrary);

		NamedAPIResource<Berry> berryResource = new NamedAPIResource<>();
		berryResource.setName("cheri");
		berryResource.setUrl(PokeApiUrl.fromClassAndId(Berry.class, 1));
		berryResource.setClazz(Berry.class);
		berryResource.setLibInstance(apiLibrary);

		cache.updateCachedPaginationResult(Pokemon.class, 0, pokemonResource);
		cache.updateCachedPaginationResult(Berry.class, 0, berryResource);

		APIResource<Pokemon> loadedPokemon = cache.getCachedPaginationResult(Pokemon.class, 0);
		APIResource<Berry> loadedBerry = cache.getCachedPaginationResult(Berry.class, 0);

		assertNotNull(loadedPokemon);
		assertNotNull(loadedBerry);
		assertEquals("bulbasaur", ((NamedAPIResource<Pokemon>) loadedPokemon).getName());
		assertEquals("cheri", ((NamedAPIResource<Berry>) loadedBerry).getName());
		assertEquals(Pokemon.class, loadedPokemon.getClazz());
		assertEquals(Berry.class, loadedBerry.getClazz());
	}

	@Test
	void testPaginationCountCaching() {
		assertNull(cache.getCachedPaginationCount(Pokemon.class));

		cache.updateCachedPaginationCount(Pokemon.class, 1302);
		assertEquals(1302, cache.getCachedPaginationCount(Pokemon.class));

		File countFile = cache.getPaginationCountCacheFile(Pokemon.class);
		assertNotNull(countFile);
		assertTrue(countFile.exists(), "Count file must exist");
		assertTrue(countFile.length() > 0, "Count file must not be empty");

		// Invalidate memory, read from disk
		cache.invalidatePagination();
		assertEquals(1302, cache.getCachedPaginationCount(Pokemon.class));
	}

	@Test
	void testPaginationInvalidationByClass() {
		NamedAPIResource<Pokemon> pokemonResource = new NamedAPIResource<>();
		pokemonResource.setName("bulbasaur");
		pokemonResource.setUrl(PokeApiUrl.fromClassAndId(Pokemon.class, 1));

		NamedAPIResource<Berry> berryResource = new NamedAPIResource<>();
		berryResource.setName("cheri");
		berryResource.setUrl(PokeApiUrl.fromClassAndId(Berry.class, 1));

		cache.updateCachedPaginationResult(Pokemon.class, 0, pokemonResource);
		cache.updateCachedPaginationResult(Berry.class, 0, berryResource);

		cache.invalidatePagination(Pokemon.class);

		// Pokemon should not be in memory cache
		APIResourcePaginationRequest<Pokemon> pokeReq = APIResourcePaginationRequest.of(Pokemon.class, 0, false);
		assertNull(cache.getMemCachePagination().getIfPresent(pokeReq));

		// Berry should still be in memory cache
		APIResourcePaginationRequest<Berry> berryReq = APIResourcePaginationRequest.of(Berry.class, 0, false);
		assertNotNull(cache.getMemCachePagination().getIfPresent(berryReq));
	}
}

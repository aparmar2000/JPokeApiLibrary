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

import aparmar.pokelibrary.PokeDataCache.APIResourceRequest;
import aparmar.pokelibrary.objects.LoadSource;
import aparmar.pokelibrary.objects.berries.Berry;
import aparmar.pokelibrary.objects.utility.APIResource;
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
	}
}

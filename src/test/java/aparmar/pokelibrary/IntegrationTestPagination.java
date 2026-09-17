package aparmar.pokelibrary;

import static aparmar.pokelibrary.utils.TestUtils.autoTestClassGetters;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aparmar.pokelibrary.objects.pokemon.Pokemon;
import lombok.val;

class IntegrationTestPagination {
	private static final File TEST_CACHE = new File("test_cache");
	private PokeApiLibrary apiLibrary;

	@BeforeEach
	void setUp() throws Exception {
		if (TEST_CACHE.exists()) {
			try (Stream<Path> walk = Files.walk(TEST_CACHE.toPath())) {
				walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
			}
		}

		Files.createDirectories(TEST_CACHE.toPath());
		
		apiLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
	}

	@Test
	void test() {
		val loadedPokemon = apiLibrary.getResourceList(Pokemon.class, 100, 100, true, PaginationCacheUsage.USE_CACHE);
		assertEquals(100, loadedPokemon.size());
		for (Pokemon loadedPokmn : loadedPokemon) {
			autoTestClassGetters(loadedPokmn);
		}

		// Verify pagination entries and count were written to disk
		File countFile = apiLibrary.getDataCache().getPaginationCountCacheFile(Pokemon.class);
		assertNotNull(countFile);
		assertTrue(countFile.exists(), "Pagination count file must exist on disk");

		for (int i = 0; i < 100; i++) {
			File pageFile = apiLibrary.getDataCache().getPaginationCacheFile(Pokemon.class, i);
			assertNotNull(pageFile);
			assertTrue(pageFile.exists(), "Pagination entry " + i + " must exist on disk: " + pageFile);
			assertTrue(pageFile.length() > 0, "Pagination entry " + i + " must not be empty");
		}

		// Second pass with a new library instance verifying disk cache usage
		PokeApiLibrary diskLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
		val cachedPokemon = diskLibrary.getResourceList(Pokemon.class, 100, 100, true, PaginationCacheUsage.USE_CACHE);
		assertEquals(100, cachedPokemon.size());
		for (int i = 0; i < 100; i++) {
			assertEquals(loadedPokemon.get(i).getName(), cachedPokemon.get(i).getName());
		}
	}

	@Test
	void testPaginationWithDisableCache() {
		// Populate first
		apiLibrary.getResourceList(Pokemon.class, 10, 10, true, PaginationCacheUsage.USE_CACHE);

		// Now query with DISABLE_CACHE - must succeed
		val freshList = apiLibrary.getResourceList(Pokemon.class, 10, 10, true, PaginationCacheUsage.DISABLE_CACHE);
		assertEquals(10, freshList.size());
	}

	@Test
	void testPaginationWithDisableCachedCount() {
		// Populate first
		apiLibrary.getResourceList(Pokemon.class, 10, 10, true, PaginationCacheUsage.USE_CACHE);

		// Now query with DISABLE_CACHED_COUNT - must succeed
		val list = apiLibrary.getResourceList(Pokemon.class, 10, 10, true, PaginationCacheUsage.DISABLE_CACHED_COUNT);
		assertEquals(10, list.size());
	}

	@Test
	void testPaginationWithDisableItemCache() {
		// Query with useItemCache = false - loaded items should have load source API
		val list = apiLibrary.getResourceList(Pokemon.class, 5, 5, false, PaginationCacheUsage.USE_CACHE);
		assertEquals(5, list.size());
		for (Pokemon p : list) {
			assertEquals(aparmar.pokelibrary.objects.LoadSource.API, p.getLoadSource());
		}
	}

}

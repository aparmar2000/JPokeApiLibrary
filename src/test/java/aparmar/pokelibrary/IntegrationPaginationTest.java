package aparmar.pokelibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aparmar.pokelibrary.objects.pokemon.Pokemon;
import lombok.val;

class IntegrationPaginationTest {
	private static final File TEST_CACHE = new File("test_cache");
	private PokeApiLibrary apiLibrary;
	
	@BeforeAll
	static void setUpAll() throws Exception {
		if (TEST_CACHE.exists()) {
			try (Stream<Path> walk = Files.walk(TEST_CACHE.toPath())) {
				walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
			}
		}
		
		Files.createDirectory(TEST_CACHE.toPath());
	}

	@BeforeEach
	void setUp() throws Exception {		
		apiLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
	}

	@Test
	void test() {
		val loadedPokemon = apiLibrary.getResourceList(Pokemon.class, 100, 100);
		assertTrue(loadedPokemon.size() == 100);
	}

}

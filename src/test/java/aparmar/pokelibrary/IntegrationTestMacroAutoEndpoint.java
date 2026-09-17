package aparmar.pokelibrary;

import static aparmar.pokelibrary.utils.GetterGraphUtils.buildGetterGraph;
import static aparmar.pokelibrary.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.LoadSource;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import aparmar.pokelibrary.utils.TestUtils.ComposedGetter;
import lombok.val;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfEnvironmentVariable(named = "allowSlowTests", matches = "True")
public class IntegrationTestMacroAutoEndpoint {
	private static final File TEST_CACHE = new File("test_cache");
	private static final int HARD_DEPTH_LIMIT = 8;
	private PokeApiLibrary apiLibrary;

	@BeforeAll
	void setUpAll() throws Exception {
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

	@ParameterizedTest(name = "testFetchableDataObjects({2})")
	@MethodSource("dataObjectsArgumentSource")
	<T extends PkmnDataObject & IPaginatedDataObject> void testDataObjects(Class<T> dataObjectClazz, Collection<ComposedGetter<T, APIResource<?>>> dataObjectGetters, String dataObjectClazzName) {
		val loadedObjects = apiLibrary.getResourceList(dataObjectClazz, 25, 25, true, PaginationCacheUsage.USE_CACHE);
		
		assertNotNull(loadedObjects, "Loaded objects should not be null for " + dataObjectClazz.getSimpleName());
		assertFalse(loadedObjects.isEmpty(), "Loaded objects should not be empty for " + dataObjectClazz.getSimpleName());
		for (T obj : loadedObjects) {
			assertNotNull(obj, "Loaded object element should not be null for " + dataObjectClazz.getSimpleName());
		}
		
		for (T loadedObject : loadedObjects) {
			for (ComposedGetter<T, APIResource<?>> composedGetter : dataObjectGetters) {
				APIResource<?> resource =  composedGetter.get(loadedObject);
				if (resource == null && composedGetter.isNullable()) {
					continue;
				}
				if (resource == null) {
					resource = composedGetter.get(loadedObject);
				}
				assertNotNull(resource, () -> "Resource must not be null for " + dataObjectClazz.getSimpleName() + " object: " + loadedObject + " in getter: " + composedGetter);
				assertNotNull(resource.getUrl(), "Resource URL must not be null");

				PkmnDataObject firstInstance = resource.get();
				assertNotNull(firstInstance, 
						"Fetchable data object should be fetchable for " + resource.getUrl().getRelativeUrl());
				autoTestClassGetters(firstInstance);

				// Verify in-memory cache
				PkmnDataObject secondInstance = resource.get();
				assertNotNull(secondInstance, "Second resolution must not return null");
				assertSame(firstInstance, secondInstance, 
						"Second resolution of " + resource.getUrl().getRelativeUrl() + " should return the same cached instance from memory");

				// Verify file cache
				PokeApiUrl url = resource.getUrl();
				File cacheFile = TEST_CACHE.toPath().resolve(Path.of(url.getRelativeUrl() + ".json")).toFile();
				assertTrue(cacheFile.exists(), "Cache file must exist for " + url.getRelativeUrl());
				assertTrue(cacheFile.length() > 0, "Cache file must not be empty for " + url.getRelativeUrl());

				// Verify file cache used
				PokeApiLibrary diskVerificationLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
				resource.setLibInstance(diskVerificationLibrary);
				PkmnDataObject diskLoaded = resource.get();
				resource.setLibInstance(apiLibrary); // restore
				assertNotNull(diskLoaded, "Resource should be loadable from disk cache for " + url.getRelativeUrl());
				assertEquals(firstInstance, diskLoaded, "Object loaded from disk cache should equal original object");
				assertEquals(LoadSource.FILE_CACHE, diskLoaded.getLoadSource(), "Object loaded from disk cache should have a source of FILE_CACHE");
			}
		}
	}
	
	private static Stream<Arguments> dataObjectsArgumentSource() {
		return findDirectlyPaginatableClasses()
				.stream()
				.map(c->Arguments.of(c, buildGetterGraph(c, HARD_DEPTH_LIMIT).values(), c.getSimpleName()));
	}
}

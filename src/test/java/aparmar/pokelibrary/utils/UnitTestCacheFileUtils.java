package aparmar.pokelibrary.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

class UnitTestCacheFileUtils {
	private static final File TEST_DIR = new File("target/test_cache_file_utils");
	private final Gson gson = new Gson();

	static class SampleData {
		private int id;
		private String name;
		private List<String> items;

		public SampleData() {}

		public SampleData(int id, String name, List<String> items) {
			this.id = id;
			this.name = name;
			this.items = items;
		}

		public int getId() { return id; }
		public String getName() { return name; }
		public List<String> getItems() { return items; }
	}

	@BeforeAll
	static void setUpAll() throws IOException {
		cleanTestDir();
		Files.createDirectories(TEST_DIR.toPath());
	}

	@BeforeEach
	void setUp() throws IOException {
		cleanTestDir();
		Files.createDirectories(TEST_DIR.toPath());
	}

	private static void cleanTestDir() throws IOException {
		if (TEST_DIR.exists()) {
			try (Stream<Path> walk = Files.walk(TEST_DIR.toPath())) {
				walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
			}
		}
	}

	@Test
	void testWriteAndReadJson() throws IOException {
		File file = new File(TEST_DIR, "sample.json");
		SampleData original = new SampleData(42, "Pikachu", List.of("thunderbolt", "quick-attack"));

		CacheFileUtils.writeJson(file, gson, original);

		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		SampleData read = CacheFileUtils.readJson(file, gson, SampleData.class);
		assertNotNull(read);
		assertEquals(42, read.getId());
		assertEquals("Pikachu", read.getName());
		assertEquals(List.of("thunderbolt", "quick-attack"), read.getItems());
	}

	@Test
	void testAtomicWriteReplacement() throws IOException {
		File file = new File(TEST_DIR, "replace.json");
		SampleData first = new SampleData(1, "Bulbasaur", List.of("tackle"));
		SampleData second = new SampleData(2, "Ivysaur", List.of("vine-whip", "razor-leaf"));

		CacheFileUtils.writeJson(file, gson, first);
		SampleData read1 = CacheFileUtils.readJson(file, gson, SampleData.class);
		assertNotNull(read1);
		assertEquals("Bulbasaur", read1.getName());

		CacheFileUtils.writeJson(file, gson, second);
		SampleData read2 = CacheFileUtils.readJson(file, gson, SampleData.class);
		assertNotNull(read2);
		assertEquals("Ivysaur", read2.getName());
		assertEquals(2, read2.getId());

		// Verify no temporary files were left behind
		try (Stream<Path> paths = Files.list(TEST_DIR.toPath())) {
			long tmpFileCount = paths.filter(p -> p.toString().endsWith(".tmp")).count();
			assertEquals(0, tmpFileCount, "Temporary files must not be left behind");
		}
	}

	@Test
	void testWriteFailureCleansUpTempFile() throws IOException {
		File file = new File(TEST_DIR, "failing.json");

		assertThrows(IOException.class, () -> {
			CacheFileUtils.writeWithLock(file, writer -> {
				writer.write("{\"partial\": ");
				throw new IOException("Simulated disk error during serialization");
			});
		});

		assertFalse(file.exists(), "Target file must not be created on write failure");

		try (Stream<Path> paths = Files.list(TEST_DIR.toPath())) {
			long tmpFileCount = paths.filter(p -> p.toString().endsWith(".tmp")).count();
			assertEquals(0, tmpFileCount, "Temporary files must be cleaned up on failure");
		}
	}

	@Test
	void testReadMissingOrEmptyFileReturnsNull() throws IOException {
		File nonExistent = new File(TEST_DIR, "non_existent.json");
		assertNull(CacheFileUtils.readJson(nonExistent, gson, SampleData.class));
		assertNull(CacheFileUtils.readJson(null, gson, SampleData.class));

		File emptyFile = new File(TEST_DIR, "empty.json");
		Files.createFile(emptyFile.toPath());
		assertNull(CacheFileUtils.readJson(emptyFile, gson, SampleData.class));
	}

	@Test
	void testConcurrentReadWriteStress() throws Exception {
		File sharedFile = new File(TEST_DIR, "concurrent_pokemon.json");

		// Initialize file with baseline data
		SampleData initial = new SampleData(0, "Init", List.of("start"));
		CacheFileUtils.writeJson(sharedFile, gson, initial);

		int numReaders = 8;
		int numWriters = 4;
		int durationMillis = 2000;

		ExecutorService executor = Executors.newFixedThreadPool(numReaders + numWriters);
		AtomicBoolean running = new AtomicBoolean(true);
		CountDownLatch startLatch = new CountDownLatch(1);
		AtomicInteger readSuccessCount = new AtomicInteger(0);
		AtomicInteger writeSuccessCount = new AtomicInteger(0);
		List<Future<Void>> futures = new ArrayList<>();

		// Launch writers
		for (int w = 0; w < numWriters; w++) {
			final int writerId = w;
			futures.add(executor.submit((Callable<Void>) () -> {
				startLatch.await();
				int iteration = 0;
				while (running.get()) {
					iteration++;
					List<String> moves = new ArrayList<>();
					for (int m = 0; m < 50; m++) {
						moves.add("move-" + writerId + "-" + iteration + "-" + m);
					}
					SampleData data = new SampleData(iteration, "Pokemon-" + writerId, moves);
					CacheFileUtils.writeJson(sharedFile, gson, data);
					writeSuccessCount.incrementAndGet();
				}
				return null;
			}));
		}

		// Launch readers
		for (int r = 0; r < numReaders; r++) {
			futures.add(executor.submit((Callable<Void>) () -> {
				startLatch.await();
				while (running.get()) {
					SampleData data = CacheFileUtils.readJson(sharedFile, gson, SampleData.class);
					assertNotNull(data, "Read result must not be null during concurrent access");
					assertNotNull(data.getName(), "Parsed data name must not be null");
					assertNotNull(data.getItems(), "Parsed data items must not be null");
					if (data.getId() == 0) {
						assertEquals(1, data.getItems().size(), "Initial baseline data has 1 item");
					} else {
						assertEquals(50, data.getItems().size(), "Parsed data must have complete items list (not truncated)");
					}
					readSuccessCount.incrementAndGet();
				}
				return null;
			}));
		}

		// Start all threads simultaneously
		startLatch.countDown();
		Thread.sleep(durationMillis);
		running.set(false);

		executor.shutdown();
		assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));

		// Check for any exceptions thrown in worker threads
		for (Future<Void> future : futures) {
			future.get();
		}

		assertTrue(writeSuccessCount.get() > 0, "At least some writes should have succeeded");
		assertTrue(readSuccessCount.get() > 0, "At least some reads should have succeeded");

		// Verify final file is clean and readable
		SampleData finalData = CacheFileUtils.readJson(sharedFile, gson, SampleData.class);
		assertNotNull(finalData);
		assertEquals(50, finalData.getItems().size());
	}
}

package aparmar.pokelibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import aparmar.pokelibrary.objects.pokemon.Pokemon;

@Timeout(value = 15, unit = TimeUnit.SECONDS)
class IntegrationTestPaginationLifecycle {
	private static final File TEST_CACHE = new File("test_cache");
	private PokeApiLibrary apiLibrary;

	@BeforeEach
	void setUp() throws Exception {
		if (!TEST_CACHE.exists()) {
			Files.createDirectories(TEST_CACHE.toPath());
		}
		apiLibrary = new PokeApiLibrary(TEST_CACHE, 5, false);
	}

	@Test
	void testThresholdBackpressure() throws Exception {
		int threshold = 4;
		try (CloseableIterator<Pokemon> iterator = apiLibrary.getPaginatedResourceIterator(
				Pokemon.class, 20, threshold, true, PaginationCacheUsage.USE_CACHE)) {

			@SuppressWarnings("resource")
			PokeApiPaginationIterator<Pokemon> concreteIter = (PokeApiPaginationIterator<Pokemon>) iterator;
			PokeApiPaginationThread<Pokemon> thread = concreteIter.getPaginationThread();

			// Wait briefly for the thread to reach threshold and pause
			long deadline = System.currentTimeMillis() + 3000;
			while (thread.isAlive() && System.currentTimeMillis() < deadline) {
				Thread.sleep(100);
			}

			int queueSize = thread.getItemQueue().size();
			assertTrue(queueSize >= threshold, "Queue should reach threshold: " + queueSize);
			// Thread should now be paused and not load far beyond threshold
			Thread.sleep(300);
			queueSize = thread.getItemQueue().size();
			assertTrue(queueSize <= threshold + 1,
					"Queue size should not significantly exceed threshold while consumer is idle, was: " + queueSize);

			// Now consume the batch to empty the queue
			for (int i = 0; i < threshold; i++) {
				Pokemon item = iterator.next();
				assertNotNull(item);
			}

			// Worker thread should detect that itemQueue is empty and replenish the next batch
			deadline = System.currentTimeMillis() + 3000;
			while (thread.getItemQueue().isEmpty() && thread.isAlive() && System.currentTimeMillis() < deadline) {
				Thread.sleep(50);
			}
			queueSize = thread.getItemQueue().size();
			assertTrue(queueSize > 0, "Queue should replenish with next batch once emptied, was: " + queueSize);
		}
	}

	@Test
	void testSingleNextDoesNotLoadAll() throws Exception {
		int threshold = 3;
		try (CloseableIterator<Pokemon> iterator = apiLibrary.getPaginatedResourceIterator(
				Pokemon.class, 20, threshold, true, PaginationCacheUsage.USE_CACHE)) {

			@SuppressWarnings("resource")
			PokeApiPaginationIterator<Pokemon> concreteIter = (PokeApiPaginationIterator<Pokemon>) iterator;
			assertTrue(iterator.hasNext());
			Pokemon pokemon = iterator.next();
			assertNotNull(pokemon);

			// Let the thread stabilize at the threshold
			Thread.sleep(300);

			int queueSize = concreteIter.getPaginationThread().getItemQueue().size();
			assertTrue(queueSize <= threshold + 1,
					"Queue should not load all entries when next() is only called once. Current size: "
							+ queueSize);
		}
	}

	@Test
	void testExplicitCloseStopsThread() throws Exception {
		CloseableIterator<Pokemon> iterator = apiLibrary.getPaginatedResourceIterator(
				Pokemon.class, 20, 5, true, PaginationCacheUsage.USE_CACHE);

		PokeApiPaginationIterator<Pokemon> concreteIter = (PokeApiPaginationIterator<Pokemon>) iterator;
		PokeApiPaginationThread<Pokemon> thread = concreteIter.getPaginationThread();

		assertTrue(thread.isAlive(), "Thread should be running");
		iterator.close();
		assertTrue(concreteIter.isClosed(), "Iterator should report closed");

		thread.join(2000);
		assertFalse(thread.isAlive(), "Thread should have terminated after explicit close()");
		assertFalse(iterator.hasNext(), "Closed iterator should not have next");
	}

	@Test
	void testStreamTryWithResourcesClosesUnderlyingThread() throws Exception {
		CloseableIterator<Pokemon> iterator = apiLibrary.getPaginatedResourceIterator(
				Pokemon.class, 20, 5, true, PaginationCacheUsage.USE_CACHE);
		PokeApiPaginationIterator<Pokemon> concreteIter = (PokeApiPaginationIterator<Pokemon>) iterator;
		PokeApiPaginationThread<Pokemon> thread = concreteIter.getPaginationThread();

		try (Stream<Pokemon> stream = iterator.stream()) {
			List<Pokemon> sample = stream.limit(2).collect(java.util.stream.Collectors.toList());
			assertEquals(2, sample.size());
		}

		thread.join(2000);
		assertFalse(thread.isAlive(), "Underlying thread should terminate when Stream is closed via try-with-resources");
	}

	@Test
	void testGetResourceListClosesThreadImmediately() throws Exception {
		// getResourceList internally uses try-with-resources on the stream
		List<Pokemon> sample = apiLibrary.getResourceList(Pokemon.class, 20, 3, true, PaginationCacheUsage.USE_CACHE);
		assertEquals(3, sample.size());
	}

	@Test
	void testDiscardDetectionViaCleaner() throws Exception {
		WeakReference<PokeApiPaginationThread<?>> threadRef = createAndAbandonIterator(apiLibrary);

		// Trigger GC to collect the unreferenced iterator and fire Cleaner
		long deadline = System.currentTimeMillis() + 4000;
		while (threadRef.get() != null && threadRef.get().isAlive() && System.currentTimeMillis() < deadline) {
			System.gc();
			System.runFinalization();
			Thread.sleep(100);
		}

		PokeApiPaginationThread<?> thread = threadRef.get();
		if (thread != null) {
			try {
				thread.join(1000);
				assertFalse(thread.isAlive(), "Thread should terminate when iterator is discarded");
			} finally {
				thread.close();
			}
		}
	}

	private WeakReference<PokeApiPaginationThread<?>> createAndAbandonIterator(PokeApiLibrary library) {
		PokeApiPaginationThread<?> thread;
		{
			CloseableIterator<Pokemon> iterator = library.getPaginatedResourceIterator(
					Pokemon.class, 20, 3, true, PaginationCacheUsage.USE_CACHE);
			PokeApiPaginationIterator<Pokemon> concreteIter = (PokeApiPaginationIterator<Pokemon>) iterator;
			// Pull 1 item so thread is actively running
			assertTrue(iterator.hasNext());
			assertNotNull(iterator.next());
			thread = concreteIter.getPaginationThread();
			iterator = null;
			concreteIter = null;
		}
		return new WeakReference<>(thread);
	}
}

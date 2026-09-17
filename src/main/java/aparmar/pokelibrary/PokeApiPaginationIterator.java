package aparmar.pokelibrary;

import java.lang.ref.Cleaner;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

import com.google.gson.Gson;

import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.AccessLevel;
import lombok.Getter;

public class PokeApiPaginationIterator<T extends PkmnDataObject & IPaginatedDataObject> implements CloseableIterator<T> {
	private static final Cleaner CLEANER = Cleaner.create();

	private static class CleanerTask implements Runnable {
		private final PokeApiPaginationThread<?> thread;

		CleanerTask(PokeApiPaginationThread<?> thread) {
			this.thread = thread;
		}

		@Override
		public void run() {
			thread.close();
		}
	}

	@Getter(value = AccessLevel.PACKAGE)
	private final PokeApiPaginationThread<T> paginationThread;
	private final ConcurrentLinkedQueue<T> itemQueue;
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final Cleaner.Cleanable cleanable;

	PokeApiPaginationIterator(PokeApiLibrary apiLibrary, Gson gson, String apiEndpoint, Class<T> clazz,
			int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage) {
		this(apiLibrary, gson, apiEndpoint, clazz, paginationSize, useItemCache, paginationCacheUsage,
				PokeApiPaginationThread.calculateDefaultThreshold(paginationSize));
	}

	PokeApiPaginationIterator(PokeApiLibrary apiLibrary, Gson gson, String apiEndpoint, Class<T> clazz,
			int paginationSize, boolean useItemCache, PaginationCacheUsage paginationCacheUsage,
			int queueThreshold) {
		super();
		this.itemQueue = new ConcurrentLinkedQueue<>();
		this.paginationThread = new PokeApiPaginationThread<>(apiLibrary, gson, apiEndpoint, clazz,
				paginationSize, useItemCache, paginationCacheUsage, itemQueue, queueThreshold);
		this.paginationThread.setConsumerReference(this);
		this.cleanable = CLEANER.register(this, new CleanerTask(paginationThread));
		this.paginationThread.start();
	}

	public boolean isClosed() {
		return closed.get();
	}

	@Override
	public void close() {
		if (closed.compareAndSet(false, true)) {
			cleanable.clean();
			paginationThread.close();
			itemQueue.clear();
		}
	}

	@Override
	public boolean hasNext() {
		waitForRefill();
		return !itemQueue.isEmpty();
	}

	@Override
	public T next() {
		waitForRefill();
		T next = itemQueue.poll();
		if (next == null) {
			throw new NoSuchElementException();
		}
		return next;
	}

	private void waitForRefill() {
		LockSupport.unpark(paginationThread);
		while (itemQueue.isEmpty() && !paginationThread.isFinished() && !closed.get()) {
			Thread.onSpinWait();
		}
	}

}

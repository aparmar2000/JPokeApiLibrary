package main.java.aparmar.pokelibrary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;

public class PokeApiPaginationIterator<T extends IPaginatedDataObject> implements Iterator<T> {		
	private final PokeApiPaginationThread<T> paginationThread;
	private final ConcurrentLinkedQueue<T> itemQueue = new ConcurrentLinkedQueue<>();
	
	PokeApiPaginationIterator(PokeApiLibrary apiLibrary, Gson gson, String apiEndpoint, Class<T> clazz, int paginationSize) {
		super();
		
		paginationThread = new PokeApiPaginationThread<T>(apiLibrary, gson, apiEndpoint, clazz, paginationSize, itemQueue);
		paginationThread.start();
	}

	@Override
	public boolean hasNext() {
		while (paginationThread.isAlive()) {
			if (!itemQueue.isEmpty()) {
				return true;
			}
		}
		return !itemQueue.isEmpty();
	}

	@Override
	public T next() {
		while (paginationThread.isAlive() && itemQueue.isEmpty()) {
			Thread.onSpinWait();
		}
		T next = itemQueue.poll();
		if (next == null) {
			throw new NoSuchElementException();
		}
		return next;
	}

}

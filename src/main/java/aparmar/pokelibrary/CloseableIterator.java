package aparmar.pokelibrary;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface CloseableIterator<T> extends Iterator<T>, AutoCloseable, Iterable<T> {
	@Override
	void close();

	@Override
	default Iterator<T> iterator() {
		return this;
	}

	default Stream<T> stream() {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED),
				false)
				.onClose(this::close);
	}
}

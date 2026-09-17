package aparmar.pokelibrary.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.locks.ReadWriteLock;

import org.jetbrains.annotations.Nullable;

import com.google.common.util.concurrent.Striped;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class providing thread-safe and atomic file read/write operations for cache management.
 * <p>
 * File writes are performed to a temporary file in the target directory and atomically moved into place,
 * preventing concurrent readers from observing partially written or truncated files.
 * In addition, an internal striped {@link ReadWriteLock} further limits concurrent file accesses.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheFileUtils {
	private static final Striped<ReadWriteLock> FILE_LOCKS = Striped.lazyWeakReadWriteLock(1024);

	@FunctionalInterface
	public interface IOFunction<T, R> {
		R apply(T input) throws IOException, JsonSyntaxException, JsonIOException;
	}

	@FunctionalInterface
	public interface IOConsumer<T> {
		void accept(T input) throws IOException, JsonSyntaxException, JsonIOException;
	}

	private static String getLockKey(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			return file.toPath().toAbsolutePath().normalize().toString();
		}
	}

	/**
	 * Reads from a cache file under a shared read lock.
	 * Returns {@code null} if the file does not exist, cannot be read, or is empty.
	 *
	 * @param file the file to read
	 * @param readerFunction function that consumes the opened {@link Reader}
	 * @param <T> return type
	 * @return the parsed object, or {@code null} if file does not exist or is empty
	 * @throws IOException if an I/O error occurs
	 */
	@Nullable
	public static <T> T readWithLock(File file, IOFunction<Reader, T> readerFunction) throws IOException {
		if (file == null) {
			return null;
		}

		ReadWriteLock rwLock = FILE_LOCKS.get(getLockKey(file));
		rwLock.readLock().lock();
		try {
			if (!file.exists() || !file.canRead() || file.length() == 0) {
				return null;
			}
			try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
				return readerFunction.apply(reader);
			}
		} finally {
			rwLock.readLock().unlock();
		}
	}

	/**
	 * Writes data to a target cache file under an exclusive write lock.
	 * The write is performed to a temporary file in the same directory and then atomically moved,
	 * guaranteeing that readers never observe partially written content.
	 *
	 * @param file the target file to write
	 * @param writerConsumer consumer that writes to the opened {@link Writer}
	 * @throws IOException if an I/O error occurs
	 */
	public static void writeWithLock(File file, IOConsumer<Writer> writerConsumer) throws IOException {
		if (file == null) {
			return;
		}

		ReadWriteLock rwLock = FILE_LOCKS.get(getLockKey(file));
		rwLock.writeLock().lock();
		Path tempPath = null;
		try {
			Path targetPath = file.toPath().toAbsolutePath().normalize();
			Path parentDir = targetPath.getParent();
			if (parentDir != null) {
				Files.createDirectories(parentDir);
			}

			String prefix = targetPath.getFileName().toString() + ".";
			tempPath = Files.createTempFile(parentDir, prefix, ".tmp");

			try (BufferedWriter writer = Files.newBufferedWriter(tempPath, StandardCharsets.UTF_8)) {
				writerConsumer.accept(writer);
				writer.flush();
			}

			try {
				Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (AtomicMoveNotSupportedException e) {
				Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			}
			tempPath = null;
		} finally {
			if (tempPath != null) {
				try {
					Files.deleteIfExists(tempPath);
				} catch (IOException ignored) {}
			}
			rwLock.writeLock().unlock();
		}
	}

	/**
	 * Reads and deserializes JSON from a cache file under a shared read lock.
	 *
	 * @param file the cache file
	 * @param gson the Gson serializer
	 * @param type the target type
	 * @param <T> the parsed type
	 * @return the parsed object, or {@code null} if file is missing/empty
	 * @throws IOException if an I/O error occurs
	 * @throws JsonSyntaxException if the JSON is malformed
	 * @throws JsonIOException if Gson encountered an I/O error during parse
	 */
	@Nullable
	public static <T> T readJson(File file, Gson gson, Type type) throws IOException, JsonSyntaxException, JsonIOException {
		if (file == null || gson == null || type == null) {
			return null;
		}
		return readWithLock(file, reader -> gson.fromJson(reader, type));
	}

	/**
	 * Reads and deserializes JSON from a cache file under a shared read lock.
	 *
	 * @param file the cache file
	 * @param gson the Gson serializer
	 * @param clazz the target class
	 * @param <T> the parsed type
	 * @return the parsed object, or {@code null} if file is missing/empty
	 * @throws IOException if an I/O error occurs
	 * @throws JsonSyntaxException if the JSON is malformed
	 * @throws JsonIOException if Gson encountered an I/O error during parse
	 */
	@Nullable
	public static <T> T readJson(File file, Gson gson, Class<T> clazz) throws IOException, JsonSyntaxException, JsonIOException {
		return readJson(file, gson, (Type) clazz);
	}

	/**
	 * Serializes an object to JSON and writes it atomically to a cache file under an exclusive write lock.
	 *
	 * @param file the target cache file
	 * @param gson the Gson serializer
	 * @param data the object to serialize
	 * @throws IOException if an I/O error occurs
	 * @throws JsonSyntaxException if the JSON serialization fails
	 * @throws JsonIOException if Gson encountered an I/O error during serialization
	 */
	public static void writeJson(File file, Gson gson, Object data) throws IOException, JsonSyntaxException, JsonIOException {
		if (file == null || gson == null || data == null) {
			return;
		}
		writeWithLock(file, writer -> gson.toJson(data, writer));
	}
}

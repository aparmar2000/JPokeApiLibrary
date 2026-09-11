package aparmar.pokelibrary.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.function.UnaryOperator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

public class ReflectionUtils {
	private static final LoadingCache<Class<?>, ReflectionReplicator<?>> REPLICATOR_CACHE = CacheBuilder
			.newBuilder()
			.maximumSize(128)
			.build(CacheLoader.from(ReflectionUtils::generateReflectionReplicator));
	
	@FunctionalInterface
	public static interface ReflectionReplicator<T> extends UnaryOperator<T> {
		public T replicate(T in);
		
		@Override
		default T apply(T t) {
			return replicate(t);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T shallowReplicate(T inst) {
		return ((ReflectionReplicator<T>)REPLICATOR_CACHE.getUnchecked(inst.getClass())).replicate(inst);
	}
	
	private static <T> ReflectionReplicator<T> generateReflectionReplicator(Class<T> clazz) {
		if (Modifier.isAbstract(clazz.getModifiers()) || Modifier.isInterface(clazz.getModifiers())) {
			throw new IllegalArgumentException(clazz+" is not a concrete class, and therefore cannot be replicated!");
		}
		
		try {
			Constructor<T> noArgsConstructor = clazz.getConstructor();
			ImmutableList<Field> clazzFields = getAllNonFinalFields(clazz);
			clazzFields.forEach(f->f.setAccessible(true));
			
			return (original) -> {
				try {
					T copy = noArgsConstructor.newInstance();
					
					for (Field field : clazzFields) {
						field.set(copy, field.get(original));
					}
					
					return copy;
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new RuntimeException("Failed to replicate", e);
				}
			};
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Failed to construct replicator", e);
		}
	}
	
    public static ImmutableList<Field> getAllNonFinalFields(Class<?> clazz) {
        ImmutableList.Builder<Field> fieldListBuilder = ImmutableList.builder();
        Class<?> currentClazz = clazz;

        while (currentClazz != null && currentClazz != Object.class) {
            for (Field field : currentClazz.getDeclaredFields()) {
                if (!Modifier.isFinal(field.getModifiers())) {
                	fieldListBuilder.add(field);
                }
            }
            
            currentClazz = currentClazz.getSuperclass(); 
        }

        return fieldListBuilder.build();
    }
}

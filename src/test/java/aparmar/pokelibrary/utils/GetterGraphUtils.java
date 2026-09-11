package aparmar.pokelibrary.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;

import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.utils.TestUtils.ComposedGetter;
import aparmar.pokelibrary.utils.TestUtils.WrappedGetter;

import static aparmar.pokelibrary.utils.TestUtils.*;

public class GetterGraphUtils {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Map<Class<?>, ComposedGetter<T, ?>> buildGetterGraph(Class<T> dataObjectClazz, int depthLimit) {
		Map<Class<?>, ComposedGetter<T, ?>> objectGetterMap = new HashMap<>();
		Set<Type> seenTypes = new HashSet<>();
		Queue<ComposedGetter<T, ?>> queue = new ArrayDeque<>();
		
		for (WrappedGetter<T, ?> getter : getClassGetters(dataObjectClazz)) {
			ComposedGetter<T, ?> cg = new ComposedGetter<>(getter);
			handleCandidateGetter(cg, seenTypes, queue, objectGetterMap, depthLimit);
		}

		while (!queue.isEmpty()) {
			ComposedGetter<T, ?> current = queue.poll();
			if (current.getDepth() >= depthLimit) {
				continue;
			}

			Class<?> currentClass = current.getReturnType();
			Type currentGenericType = current.getGenericReturnType();
			ComposedGetter<T, ?> composed = null;

			if (APIResource.class.isAssignableFrom(currentClass)) {
				composed = getterGraphHandleApiResource(current, currentGenericType);
			} else if (Collection.class.isAssignableFrom(currentClass)) {
				composed = getterGraphHandleCollection(current, currentGenericType);
			} else if (currentClass.isArray()) {
				composed = getterGraphHandleArray(current, currentClass);
			} else if (isLibraryDataObject(currentClass)) {
				for (Object obj : getClassGetters((Class) currentClass)) {
					WrappedGetter childGetter = (WrappedGetter) obj;
					ComposedGetter nextStep = new ComposedGetter(childGetter);
					composed = current.andThen(nextStep);
					handleCandidateGetter(composed, seenTypes, queue, objectGetterMap, depthLimit);
				}
				composed = null;
			}

			if (composed != null) {
				handleCandidateGetter(composed, seenTypes, queue, objectGetterMap, depthLimit);
			}
		}
		
		return objectGetterMap;
	}

	private static <T> void handleCandidateGetter(ComposedGetter<T, ?> candidate,
			Set<Type> seenTypes, Queue<ComposedGetter<T, ?>> queue,
			Map<Class<?>, ComposedGetter<T, ?>> objectGetterMap, 
			int depthLimit) {
		if (candidate == null || candidate.getDepth() > depthLimit) {
			return;
		}

		Type genericReturnType = candidate.getGenericReturnType();
		if (seenTypes.add(genericReturnType)) {
			queue.add(candidate);
		}

		Class<?> returnClass = candidate.getReturnType();
		if (APIResource.class.isAssignableFrom(returnClass)) {
			objectGetterMap.merge(returnClass, candidate,
					(a, b) -> a.getDepth() <= b.getDepth() ? a : b);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> ComposedGetter<T, ?> getterGraphHandleApiResource(ComposedGetter<T, ?> current, Type currentGenericType) {
		Type targetType = currentGenericType;
		if (currentGenericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) currentGenericType;
			Type[] args = pt.getActualTypeArguments();
			if (args.length > 0) {
				targetType = args[0];
			}
		}
		
		if (targetType instanceof Class) {
			Class<?> targetClass = (Class<?>) targetType;
			ComposedGetter nextStep = new ComposedGetter(
					(Function) (res -> {
						if (res instanceof APIResource) {
							APIResource<?> apiRes = (APIResource<?>) res;
							return apiRes.get();
						}
						return null;
					}),
					"anonymous#getAPIResource["+targetClass.getSimpleName()+"]",
					false,
					targetType,
					targetClass
			);
			return current.andThen(nextStep);
		}
		
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> ComposedGetter<T, ?> getterGraphHandleCollection(ComposedGetter<T, ?> current, Type currentGenericType) {
		Type elemType = currentGenericType;
		if (currentGenericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) currentGenericType;
			Type[] args = pt.getActualTypeArguments();
			if (args.length > 0) {
				elemType = args[0];
			}
		}
		
		Class<?> elemClass = null;
		if (elemType instanceof Class) {
			elemClass = (Class<?>) elemType;
		} else if (elemType instanceof ParameterizedType) {
			Type raw = ((ParameterizedType) elemType).getRawType();
			if (raw instanceof Class) {
				elemClass = (Class<?>) raw;
			}
		} else if (elemType instanceof WildcardType) {
			WildcardType wt = (WildcardType) elemType;
			if (wt.getUpperBounds().length > 0 && wt.getUpperBounds()[0] instanceof Class) {
				elemClass = (Class<?>) wt.getUpperBounds()[0];
			}
		}

		if (elemClass != null) {
			ComposedGetter nextStep = new ComposedGetter(
					(Function) (col -> (col instanceof Collection && !((Collection<?>) col).isEmpty())
							? ((Collection<?>) col).iterator().next() : null),
					"anonymous#getCollectionValue["+elemClass.getSimpleName()+"]",
					false,
					elemType,
					elemClass
			);
			return current.andThen(nextStep);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> ComposedGetter<T, ?> getterGraphHandleArray(ComposedGetter<T, ?> current, Class<?> currentClass) {
		Class<?> elemClass = currentClass.getComponentType();
		ComposedGetter nextStep = new ComposedGetter(
				(Function) (arr -> (arr != null && ((Object[]) arr).length > 0) ? ((Object[]) arr)[0] : null),
				"anonymous#getArrayValue["+elemClass.getSimpleName()+"]",
				false,
				elemClass
		);
		
		return current.andThen(nextStep);
	}
}

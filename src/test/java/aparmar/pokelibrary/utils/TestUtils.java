package aparmar.pokelibrary.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.reflect.ClassPath;

import aparmar.pokelibrary.MacroAutoEndpointTest;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

public class TestUtils {
	@SuppressWarnings("rawtypes")
	private static ListMultimap<Class, WrappedGetter> classGetterCache = MultimapBuilder.hashKeys().arrayListValues().build();
	
	@Value
	public static class FunctionWithMeta<T, R> implements Function<T, R> {
		String name;
		/** Not 'can return null', but 'is returning null accepted' */
		boolean nullable;
		Function<T,R> wrapped;
		
		@Override
		public R apply(T t) {
			return wrapped.apply(t);
		}
		
	}
	
	@Value
	@AllArgsConstructor
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static class ComposedGetter<T, R> {
		@Getter(value = AccessLevel.PRIVATE)
		ImmutableList<FunctionWithMeta> getterFunctions;
		
		Type genericReturnType;
		Class<R> returnType;
		
		public int getDepth() {
			return getterFunctions.size();
		}
		
		public boolean isNullable() {
			return getterFunctions.stream().anyMatch(FunctionWithMeta::isNullable);
		}
		
		public R get(T inst) {
			Object result = inst;
			for (Function getterFunc : getterFunctions) {
				result = getterFunc.apply(result);
			}
			return (R) result;
		}
		
		public ComposedGetter(WrappedGetter<T, R> getter) {
			getterFunctions = ImmutableList.of(new FunctionWithMeta(
					getter.getWrappedMethod().getName(), 
					getter.getWrappedMethod().isAnnotationPresent(OptionalField.class),
					i->getter.get((T) i)
					));
			genericReturnType = getter.getGenericReturnType();
			returnType = getter.getReturnType();
		}
		
		public ComposedGetter(Function<T, R> getterFunction, String getterName, boolean nullable, Class<R> returnType) {
			this(getterFunction, getterName, nullable, returnType, returnType);
		}
		
		public ComposedGetter(Function<T, R> getterFunction, String getterName, boolean nullable, Type genericReturnType, Class<R> returnType) {
			getterFunctions = ImmutableList.of(new FunctionWithMeta(getterName, nullable, getterFunction));
			this.genericReturnType = genericReturnType;
			this.returnType = returnType;
		}
		
		public <R2> ComposedGetter<T, R2> andThen(ComposedGetter<R, R2> next) {
			return new ComposedGetter<T, R2>(
					ImmutableList.<FunctionWithMeta>builder()
						.addAll(getterFunctions)
						.addAll(next.getGetterFunctions())
						.build(),
					next.genericReturnType,
					next.returnType
			);
		}
	}
	
	@Value
	public static class WrappedGetter<T, R> {
		Method wrappedMethod;
		
		@SuppressWarnings("unchecked")
		R get(T inst) {
			if (inst == null) {
				return null;
			}
			try {
				return (R) wrappedMethod.invoke(inst);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException("Failed to invoke getter: " + wrappedMethod.getName(), e);
			}
		}
		
		public Type getGenericReturnType() {
			return wrappedMethod.getGenericReturnType();
		}
		
		@SuppressWarnings("unchecked")
		public Class<R> getReturnType() {
			return (Class<R>) wrappedMethod.getReturnType();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<WrappedGetter<T, ?>> getClassGetters(Class<T> dataObjectClazz) {
		if (classGetterCache.containsKey(dataObjectClazz)) {
			return (List<WrappedGetter<T, ?>>)(List)classGetterCache.get(dataObjectClazz);
		}
		
		ArrayList<WrappedGetter<T, ?>> getters = new ArrayList<>();
		
		for (Method objectMethod : dataObjectClazz.getDeclaredMethods()) {
			if (Modifier.isStatic(objectMethod.getModifiers())) { continue; }
			if (!Modifier.isPublic(objectMethod.getModifiers())) { continue; }
			if (objectMethod.getParameterCount() != 0) { continue; }
			if (objectMethod.getReturnType().equals(Void.TYPE)) { continue; }
			
			getters.add(new WrappedGetter<>(objectMethod));
		}
		
		classGetterCache.putAll(dataObjectClazz, getters);
		return getters;
	}
	
	public static boolean isLibraryDataObject(Class<?> clazz) {
		return clazz != null
				&& !Modifier.isAbstract(clazz.getModifiers())
				&& PkmnDataObject.class.isAssignableFrom(clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void autoTestClassGetters(T instance) {
		Class<T> clazz = (Class<T>) instance.getClass();
		String instName = instance.toString();
		try {
			Method nameGetter = clazz.getMethod("getName");
			if (nameGetter != null && Modifier.isPublic(nameGetter.getModifiers())) {
				instName = (String) nameGetter.invoke(instance);
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) { }
		
		for (WrappedGetter<T, ?> clazzGetter : getClassGetters(clazz)) {
			Object result = clazzGetter.get(instance);
			
			if (!clazzGetter.wrappedMethod.isAnnotationPresent(OptionalField.class)) {
				assertNotNull(result, String.format(
						"Expected %s#%s to return a non-null value, but it was null in %s!", 
						clazz.getSimpleName(), 
						clazzGetter.getWrappedMethod().getName(),
						instName
						));
				
				if (Collection.class.isAssignableFrom(result.getClass())) {
					assertNotEquals(0, ((Collection)result).size(), String.format(
							"Expected %s#%s to return a non-empty value, but it was empty in %s!", 
							clazz.getSimpleName(), 
							clazzGetter.getWrappedMethod().getName(),
							instName
							));
				} else if (result.getClass().isArray()) {
					assertNotEquals(0, ((Object[])result).length, String.format(
							"Expected %s#%s to return a non-empty value, but it was empty in %s!", 
							clazz.getSimpleName(), 
							clazzGetter.getWrappedMethod().getName(),
							instName
							));
				}
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	public static Set<Class<? extends IPaginatedDataObject>> findDirectlyPaginatableClasses() {
		try {
			ClassPath cp = ClassPath.from(MacroAutoEndpointTest.class.getClassLoader());
			return cp.getTopLevelClassesRecursive("aparmar.pokelibrary.objects").stream()
					.map(ClassPath.ClassInfo::load)
					.filter(IPaginatedDataObject.class::isAssignableFrom)
					.filter(c -> c.isAnnotationPresent(ApiPath.class))
					.filter(c -> !c.isInterface() && !Modifier.isAbstract(c.getModifiers()))
					.map(c -> (Class<? extends IPaginatedDataObject>) c)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		} catch (IOException e) {
			throw new RuntimeException("Failed to scan classes", e);
		}
	}
}

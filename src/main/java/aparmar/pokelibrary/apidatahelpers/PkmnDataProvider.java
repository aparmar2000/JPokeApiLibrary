package aparmar.pokelibrary.apidatahelpers;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import lombok.val;
import lombok.extern.java.Log;

@Log
public abstract class PkmnDataProvider<K extends PkmnDataProvider.INamedEnum, V extends IPaginatedDataObject> implements Map<K, V> {
	public static interface INamedEnum {
		public String getName();
	}
	
	protected final Class<K> keyClazz;
	protected final Class<V> valueClazz;

	protected final HashMap<String, K> nameInverseMap = new HashMap<>();
	protected final HashMap<String, V> dataObjectMap;
	
	public PkmnDataProvider(PokeApiLibrary pokeApi, Class<K> keyClazz, Class<V> valueClazz) {
		long startTime = System.currentTimeMillis();
		if (!keyClazz.isEnum()) {
			throw new IllegalArgumentException(String.format("Key class must be an enum, but was %s!", keyClazz.getName()));
		}
		
		this.keyClazz = keyClazz;
		this.valueClazz = valueClazz;
		
		dataObjectMap = pokeApi.getPaginatedResourceStream(valueClazz)
			.collect(Collectors.toMap(this::getStringName, Function.identity(), (a,b)->a, HashMap::new));
		
		for (K presetEnum : keyClazz.getEnumConstants()) {
			if (!dataObjectMap.containsKey(presetEnum.getName())) {
				log.severe(String.format("%s %s not found in data!", keyClazz.getSimpleName(), presetEnum));
			}
			nameInverseMap.put(presetEnum.getName(), presetEnum);
		}
		List<String> missingEntries = new ArrayList<String>();
		for (val dataEntry : dataObjectMap.entrySet()) {
			if (!nameInverseMap.containsKey(dataEntry.getKey())) {
				missingEntries.add(dataEntry.getKey());
			}
			try {
				preloadResources(dataEntry.getValue(), 2);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.warning(String.format("Failed to preload resources for %s: %s", dataEntry.getValue(), e.getLocalizedMessage()));
			}
		}
		
		if (!missingEntries.isEmpty()) {
			StringBuilder sb = new StringBuilder(String.format("%s missing %d entry/ies for %s:", keyClazz.getSimpleName(), missingEntries.size(), valueClazz.getSimpleName()));
			for (String missingEntry : missingEntries) {
				String entryEnumName = missingEntry.toUpperCase().replaceAll("[- ]", "_");
				sb.append(String.format("\n%s(\"%s\")", entryEnumName, missingEntry));
			}
			log.info(sb.toString());
		}
		
		String valueClassNamePlural = valueClazz.getSimpleName();
		if (valueClassNamePlural.endsWith("y")) {
			valueClassNamePlural = valueClassNamePlural.substring(0, valueClassNamePlural.length()-1) + "ies";
		} else if (valueClassNamePlural.endsWith("s")) {
			valueClassNamePlural += "'";
		} else {
			valueClassNamePlural += "s";
		}
		Duration loadTime = Duration.ofMillis(System.currentTimeMillis()-startTime);
		log.info(String.format("Loaded %d Pokemon %s in %,.3d.", 
					size(), 
					valueClassNamePlural,
					loadTime.toSeconds() + loadTime.toMillisPart()/1000.
				));
	}
	
	protected abstract String getStringName(V value);
	
	protected void preloadResources(Object dataObject, int maxDepth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		preloadResources(dataObject, maxDepth, 0);
	}
	
	protected void preloadResources(Object dataObject, int maxDepth, int depth) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (val objMethod : dataObject.getClass().getMethods()) {
			if (objMethod.getParameterCount() > 0) { continue; }
			if (!APIResource.class.isAssignableFrom(objMethod.getReturnType())) { continue; }
			APIResource<?> resultResource = (APIResource<?>) objMethod.invoke(dataObject);
			if (resultResource != null) {
				Object result = resultResource.get();
				if (depth<maxDepth) {
					preloadResources(result, maxDepth, depth+1);
				}
			}
		}
	}
	
	// --- Map Methods

	@Override
	public int size() {
		return dataObjectMap.size();
	}

	@Override
	public boolean isEmpty() {
		return dataObjectMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		if (!keyClazz.isAssignableFrom(key.getClass())) {
			return false;
		}
		return dataObjectMap.containsKey(((INamedEnum)key).getName());
	}

	@Override
	public boolean containsValue(Object value) {
		return dataObjectMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		if (!keyClazz.isAssignableFrom(key.getClass())) {
			return null;
		}
		return dataObjectMap.get(((INamedEnum)key).getName());
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException(String.format("%s is read-only!", this.getClass().getSimpleName()));
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException(String.format("%s is read-only!", this.getClass().getSimpleName()));
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException(String.format("%s is read-only!", this.getClass().getSimpleName()));
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException(String.format("%s is read-only!", this.getClass().getSimpleName()));
	}

	@Override
	public Set<K> keySet() {
		return dataObjectMap.keySet()
				.stream()
				.map(nameInverseMap::get)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Collection<V> values() {
		return ImmutableList.copyOf(dataObjectMap.values());
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return dataObjectMap.entrySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(e->nameInverseMap.get(e.getKey()), e->e.getValue()))
				.entrySet();
	}
}

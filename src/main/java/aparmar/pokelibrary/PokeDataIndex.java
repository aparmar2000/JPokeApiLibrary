package aparmar.pokelibrary;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.PkmnDataObject;

public class PokeDataIndex<T extends PkmnDataObject & IEnumerablePkmnData> {
	private final Map<Integer, T> index;
	
	PokeDataIndex(PokeApiLibrary pokeApiLibrary, Class<T> dataClazz) {
		this.index = pokeApiLibrary.getPaginatedResourceStream(dataClazz)
			.collect(ImmutableMap.toImmutableMap(IEnumerablePkmnData::getId, Function.identity()));
	}
	
	@SuppressWarnings("unchecked")
	public T[] getValues() {
		return (T[]) index.values().toArray();
	}
	
	@Nullable
	public T getById(int id) {
		return index.get(id);
	}
	public Optional<T> tryGetById(int id) {
		return Optional.ofNullable(getById(id));
	}
}

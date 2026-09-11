package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;

@Data
public class APIResource<T extends PkmnDataObject> {
	private transient PokeApiLibrary libInstance;
	private transient Class<T> clazz;
	private PokeApiUrl url;
	
	public T get() {
		try {
			return libInstance.getResource(this);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}

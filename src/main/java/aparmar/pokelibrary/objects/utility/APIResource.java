package main.java.aparmar.pokelibrary.objects.utility;

import lombok.Data;
import main.java.aparmar.pokelibrary.PokeApiLibrary;

@Data
public class APIResource<T> {
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

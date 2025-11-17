package main.java.aparmar.pokelibrary.objects.utility;

import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import main.java.aparmar.pokelibrary.PokeApiLibrary;

@Data
public class APIResource<T> {
	private PokeApiLibrary libInstance;
	@Getter(value = AccessLevel.PROTECTED)
	private Class<T> clazz;
	private String url;
	
	public T get() {
		try {
			return libInstance.getResourceByUrl(this, clazz);
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

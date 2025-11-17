package main.java.aparmar.pokelibrary.utils.serialization;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lombok.RequiredArgsConstructor;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;

public class APIResourceTypeAdapterFactory implements TypeAdapterFactory {

	@SuppressWarnings("rawtypes")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		if (!APIResource.class.isAssignableFrom(type.getRawType())) {
			return null;
		}
		
		TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

		Class targetClazz = null;
		
		Type objectType = type.getType();		
		if (objectType instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) objectType;
			if (pType.getActualTypeArguments().length > 0) {
				Type typeArg = pType.getActualTypeArguments()[0];

				if (typeArg instanceof Class) {
					targetClazz = (Class) typeArg;
				}
			}
		}
		
		if (targetClazz == null) {
			return null;
		}
		
		return new APIResourceTypeAdapter<T>(delegate, targetClazz).nullSafe();
	}
	
	@RequiredArgsConstructor
	protected static class APIResourceTypeAdapter<T> extends TypeAdapter<T> {
		private final TypeAdapter<T> delegate;
		@SuppressWarnings("rawtypes")
		private final Class targetClazz;

		@Override
		public void write(JsonWriter out, T value) throws IOException {
			delegate.write(out, value);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public T read(JsonReader in) throws IOException {
			APIResource obj = (APIResource) delegate.read(in);
			obj.setClazz(targetClazz);
			
			return (T) obj;
		}
		
	}

}

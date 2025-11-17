package aparmar.pokelibrary.utils.serialization;

import java.io.IOException;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PokeApiUrlTypeAdapter extends TypeAdapter<PokeApiUrl> {
	private final PokeApiLibrary rootApiLibrary;

	@Override
	public void write(JsonWriter out, PokeApiUrl value) throws IOException {
		out.value(value.getUrl());
	}

	@Override
	public PokeApiUrl read(JsonReader in) throws IOException {
		String url = in.nextString();
		try {
			return rootApiLibrary.getPokeApiUrlFromString(url);
		} catch (Throwable e) {
			throw new JsonParseException(e);
		}
	}

}

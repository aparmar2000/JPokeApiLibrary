package aparmar.pokelibrary.utils.serialization;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import aparmar.pokelibrary.PokeApiLibrary;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RootLibraryTypeAdapter extends TypeAdapter<PokeApiLibrary> {
	private final PokeApiLibrary rootApiLibrary;

	@Override
	public void write(JsonWriter out, PokeApiLibrary value) throws IOException {}

	@Override
	public PokeApiLibrary read(JsonReader in) throws IOException {
		return rootApiLibrary;
	}

}

package main.java.aparmar.pokelibrary.utils.serialization;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import lombok.RequiredArgsConstructor;
import main.java.aparmar.pokelibrary.PokeApiLibrary;

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

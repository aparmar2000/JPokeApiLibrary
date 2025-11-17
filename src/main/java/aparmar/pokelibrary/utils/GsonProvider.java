package main.java.aparmar.pokelibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.java.aparmar.pokelibrary.PokeApiLibrary;
import main.java.aparmar.pokelibrary.objects.utility.PokeApiUrl;
import main.java.aparmar.pokelibrary.utils.serialization.APIResourceTypeAdapterFactory;
import main.java.aparmar.pokelibrary.utils.serialization.PokeApiUrlTypeAdapter;
import main.java.aparmar.pokelibrary.utils.serialization.RootLibraryTypeAdapter;

public class GsonProvider {
	public static Gson buildGsonInstance(PokeApiLibrary pokeApiLibrary) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter(PokeApiLibrary.class, new RootLibraryTypeAdapter(pokeApiLibrary));
		gsonBuilder.registerTypeAdapterFactory(new APIResourceTypeAdapterFactory(pokeApiLibrary));
		gsonBuilder.registerTypeAdapter(PokeApiUrl.class, new PokeApiUrlTypeAdapter(pokeApiLibrary).nullSafe());
		
		return gsonBuilder.create();
	}
}

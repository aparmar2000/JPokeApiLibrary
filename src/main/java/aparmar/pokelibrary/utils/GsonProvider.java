package aparmar.pokelibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.utility.PokeApiUrl;
import aparmar.pokelibrary.utils.serialization.APIResourceTypeAdapterFactory;
import aparmar.pokelibrary.utils.serialization.PokeApiUrlTypeAdapter;
import aparmar.pokelibrary.utils.serialization.RootLibraryTypeAdapter;

public class GsonProvider {
	public static Gson buildGsonInstance(PokeApiLibrary pokeApiLibrary) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		gsonBuilder.registerTypeAdapter(PokeApiLibrary.class, new RootLibraryTypeAdapter(pokeApiLibrary));
		gsonBuilder.registerTypeAdapterFactory(new APIResourceTypeAdapterFactory(pokeApiLibrary));
		gsonBuilder.registerTypeAdapter(PokeApiUrl.class, new PokeApiUrlTypeAdapter(pokeApiLibrary).nullSafe());
		
		return gsonBuilder.create();
	}
}

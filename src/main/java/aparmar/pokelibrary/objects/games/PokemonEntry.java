package main.java.aparmar.pokelibrary.objects.games;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonEntry {
    @SerializedName("entry_number")
    private int entryNumber;
    @SerializedName("pokemon_species")
    private NamedAPIResource pokemonSpecies;
}

package main.java.aparmar.pokelibrary.objects.locations;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PalParkEncounterSpecies {
    @SerializedName("base_score")
    private int baseScore;
    private int rate;
    @SerializedName("pokemon_species")
    private NamedAPIResource<PokemonSpecies> pokemonSpecies;
}

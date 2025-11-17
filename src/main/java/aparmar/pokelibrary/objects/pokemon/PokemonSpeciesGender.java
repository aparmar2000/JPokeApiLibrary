package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonSpeciesGender {
    private int rate;
    @SerializedName("pokemon_species")
    private NamedAPIResource<PokemonSpecies> pokemonSpecies;
}

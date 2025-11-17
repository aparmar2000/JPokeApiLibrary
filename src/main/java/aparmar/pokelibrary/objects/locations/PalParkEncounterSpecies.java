package aparmar.pokelibrary.objects.locations;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PalParkEncounterSpecies {
    @SerializedName("base_score")
    private int baseScore;
    private int rate;
    @SerializedName("pokemon_species")
    private NamedAPIResource<PokemonSpecies> pokemonSpecies;
}

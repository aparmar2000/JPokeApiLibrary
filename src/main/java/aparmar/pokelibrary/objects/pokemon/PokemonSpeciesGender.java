package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonSpeciesGender {
    private int rate;
    @SerializedName("pokemon_species")
    private NamedAPIResource<PokemonSpecies> pokemonSpecies;
}

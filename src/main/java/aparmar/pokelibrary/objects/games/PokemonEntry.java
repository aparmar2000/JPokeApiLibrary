package aparmar.pokelibrary.objects.games;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonEntry {
    @SerializedName("entry_number")
    private int entryNumber;
    @SerializedName("pokemon_species")
    private NamedAPIResource<PokemonSpecies> pokemonSpecies;
}

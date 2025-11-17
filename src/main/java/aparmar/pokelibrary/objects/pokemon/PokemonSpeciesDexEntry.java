package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.Pokedex;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonSpeciesDexEntry {
    @SerializedName("entry_number")
    private int entryNumber;
    private NamedAPIResource<Pokedex> pokedex;
}

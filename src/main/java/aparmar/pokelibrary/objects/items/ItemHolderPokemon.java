package aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.Pokemon;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ItemHolderPokemon {
    private NamedAPIResource<Pokemon> pokemon;
    @SerializedName("version_details")
    private List<ItemHolderPokemonVersionDetail> versionDetails;
}

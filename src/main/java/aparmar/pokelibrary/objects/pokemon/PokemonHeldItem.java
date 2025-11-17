package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonHeldItem {
    private NamedAPIResource<Item> item;
    @SerializedName("version_details")
    private List<PokemonHeldItemVersion> versionDetails;
}

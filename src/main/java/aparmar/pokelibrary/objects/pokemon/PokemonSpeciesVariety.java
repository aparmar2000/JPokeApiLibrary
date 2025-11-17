package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonSpeciesVariety {
    @SerializedName("is_default")
    private boolean isDefault;
    private NamedAPIResource<Pokemon> pokemon;
}

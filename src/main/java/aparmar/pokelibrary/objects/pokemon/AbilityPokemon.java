package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class AbilityPokemon {
    @SerializedName("is_hidden")
    private boolean isHidden;
    private int slot;
    private NamedAPIResource<Pokemon> pokemon;
}

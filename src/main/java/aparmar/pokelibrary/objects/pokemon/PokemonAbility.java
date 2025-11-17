package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonAbility {
    @SerializedName("is_hidden")
    private boolean isHidden;
    private int slot;
    private NamedAPIResource<Ability> ability;
}

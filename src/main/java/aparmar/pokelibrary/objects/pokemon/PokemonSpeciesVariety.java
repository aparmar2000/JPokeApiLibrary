package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonSpeciesVariety {
    @SerializedName("is_default")
    private boolean isDefault;
    private NamedAPIResource<Pokemon> pokemon;
}

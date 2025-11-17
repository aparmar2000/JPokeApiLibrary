package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonAbility {
    @SerializedName("is_hidden")
    private boolean isHidden;
    private int slot;
    private NamedAPIResource ability;
}

package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PokemonFormSprites {
    @SerializedName("front_default")
    private String frontDefault;
    @SerializedName("front_shiny")
    private String frontShiny;
    @SerializedName("back_default")
    private String backDefault;
    @SerializedName("back_shiny")
    private String backShiny;
}

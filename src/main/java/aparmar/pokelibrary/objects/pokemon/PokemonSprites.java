package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PokemonSprites {
    @SerializedName("front_default")
    private String frontDefault;
    @SerializedName("front_shiny")
    private String frontShiny;
    @SerializedName("front_female")
    private String frontFemale;
    @SerializedName("front_shiny_female")
    private String frontShinyFemale;
    @SerializedName("back_default")
    private String backDefault;
    @SerializedName("back_shiny")
    private String backShiny;
    @SerializedName("back_female")
    private String backFemale;
    @SerializedName("back_shiny_female")
    private String backShinyFemale;
}

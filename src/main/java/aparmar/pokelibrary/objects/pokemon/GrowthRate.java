package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class GrowthRate {
    private int id;
    private String name;
    private String formula;
    private List<Description> descriptions;
    private List<GrowthRateExperienceLevel> levels;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
}

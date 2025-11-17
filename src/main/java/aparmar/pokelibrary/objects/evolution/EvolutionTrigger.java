package main.java.aparmar.pokelibrary.objects.evolution;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EvolutionTrigger {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource> pokemonSpecies;
}

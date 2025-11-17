package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonShape {
    private int id;
    private String name;
    @SerializedName("awesome_names")
    private List<AwesomeName> awesomeNames;
    private List<Name> names;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource> pokemonSpecies;
}

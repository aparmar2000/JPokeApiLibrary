package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonColor {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource> pokemonSpecies;
}

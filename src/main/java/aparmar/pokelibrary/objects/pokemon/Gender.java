package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Gender {
    private int id;
    private String name;
    @SerializedName("pokemon_species_details")
    private List<PokemonSpeciesGender> pokemonSpeciesDetails;
    @SerializedName("required_for_evolution")
    private List<NamedAPIResource> requiredForEvolution;
}

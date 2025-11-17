package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class VersionGroup {
    private int id;
    private String name;
    private int order;
    private NamedAPIResource generation;
    @SerializedName("move_learn_methods")
    private List<NamedAPIResource> moveLearnMethods;
    private List<NamedAPIResource> pokedexes;
    private List<NamedAPIResource> regions;
    private List<NamedAPIResource> versions;
}

package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Generation {
    private int id;
    private String name;
    private List<NamedAPIResource> abilities;
    private List<Name> names;
    @SerializedName("main_region")
    private NamedAPIResource mainRegion;
    private List<NamedAPIResource> moves;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource> pokemonSpecies;
    private List<NamedAPIResource> types;
    @SerializedName("version_groups")
    private List<NamedAPIResource> versionGroups;
}

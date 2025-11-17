package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Region {
    private int id;
    private List<NamedAPIResource> locations;
    private String name;
    private List<Name> names;
    @SerializedName("main_generation")
    private NamedAPIResource mainGeneration;
    private List<NamedAPIResource> pokedexes;
    @SerializedName("version_groups")
    private List<NamedAPIResource> versionGroups;
}

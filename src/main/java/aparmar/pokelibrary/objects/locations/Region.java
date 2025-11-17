package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.games.Pokedex;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Region {
    private int id;
    private List<NamedAPIResource<Location>> locations;
    private String name;
    private List<Name> names;
    @SerializedName("main_generation")
    private NamedAPIResource<Generation> mainGeneration;
    private List<NamedAPIResource<Pokedex>> pokedexes;
    @SerializedName("version_groups")
    private List<NamedAPIResource<VersionGroup>> versionGroups;
}

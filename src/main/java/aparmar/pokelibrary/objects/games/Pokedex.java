package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.locations.Region;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Pokedex {
    private int id;
    private String name;
    @SerializedName("is_main_series")
    private boolean isMainSeries;
    private List<Description> descriptions;
    private List<Name> names;
    @SerializedName("pokemon_entries")
    private List<PokemonEntry> pokemonEntries;
    private NamedAPIResource<Region> region;
    @SerializedName("version_groups")
    private List<NamedAPIResource<VersionGroup>> versionGroups;
}

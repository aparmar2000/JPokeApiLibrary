package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class Pokedex {
    private int id;
    private String name;
    @SerializedName("is_main_series")
    private boolean isMainSeries;
    private List<PkmnDescription> descriptions;
    private List<PkmnName> names;
    @SerializedName("pokemon_entries")
    private List<PokemonEntry> pokemonEntries;
    private NamedAPIResource<Region> region;
    @SerializedName("version_groups")
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

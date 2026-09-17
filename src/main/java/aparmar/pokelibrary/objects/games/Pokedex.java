package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ToString(callSuper = true)
public class Pokedex extends PkmnNamedDataObject {
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

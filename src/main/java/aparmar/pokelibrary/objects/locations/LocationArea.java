package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.encounters.EncounterMethodRate;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("location-area")
public class LocationArea implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("game_index")
    private int gameIndex;
    @SerializedName("encounter_method_rates")
    private List<EncounterMethodRate> encounterMethodRates;
    private NamedAPIResource<Location> location;
    private List<PkmnName> names;
    @SerializedName("pokemon_encounters")
    private List<PokemonEncounter> pokemonEncounters;
}

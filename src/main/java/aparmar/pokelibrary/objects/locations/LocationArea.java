package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.encounters.EncounterMethodRate;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("location-area")
public class LocationArea extends PkmnNamedDataObject implements IPaginatedDataObject {
    @SerializedName("game_index")
    private int gameIndex;
    @SerializedName("encounter_method_rates")
    private List<EncounterMethodRate> encounterMethodRates;
    private NamedAPIResource<Location> location;
    private List<PkmnName> names;
    @SerializedName("pokemon_encounters")
    private List<PokemonEncounter> pokemonEncounters;
}

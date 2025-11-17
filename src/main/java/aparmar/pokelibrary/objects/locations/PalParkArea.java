package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import lombok.Data;

@Data
@ApiPath("pal-park-area")
public class PalParkArea implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<PkmnName> names;
    @SerializedName("pokemon_encounters")
    private List<PalParkEncounterSpecies> pokemonEncounters;
}

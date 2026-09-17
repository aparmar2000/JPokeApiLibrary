package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("pal-park-area")
@ToString(callSuper = true)
public class PalParkArea extends PkmnNamedDataObject implements IPaginatedDataObject {
    private List<PkmnName> names;
    @SerializedName("pokemon_encounters")
    private List<PalParkEncounterSpecies> pokemonEncounters;
}

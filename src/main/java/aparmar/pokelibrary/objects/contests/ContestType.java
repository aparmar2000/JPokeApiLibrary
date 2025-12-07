package aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.berries.BerryFlavor;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("contest-type")
public class ContestType implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    @SerializedName("berry_flavor")
    private NamedAPIResource<BerryFlavor> berryFlavor;
    private List<ContestName> names;
}

package aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.contests.ContestType;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("berry-flavor")
public class BerryFlavor implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    private List<BerryFlavorMap> berries;
    @SerializedName("contest_type")
    private NamedAPIResource<ContestType> contestType;
    private List<PkmnName> names;
}

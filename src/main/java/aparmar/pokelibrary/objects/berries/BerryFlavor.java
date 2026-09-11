package aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.contests.ContestType;
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
@ApiPath("berry-flavor")
public class BerryFlavor extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<BerryFlavorMap> berries;
    @SerializedName("contest_type")
    private NamedAPIResource<ContestType> contestType;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.berries.BerryFlavor;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("contest-type")
public class ContestType extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @SerializedName("berry_flavor")
    private NamedAPIResource<BerryFlavor> berryFlavor;
    private List<ContestName> names;
}

package aparmar.pokelibrary.objects.berries;

import java.util.List;

import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
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
@ApiPath("berry-firmness")
@ToString(callSuper = true)
public class BerryFirmness extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<NamedAPIResource<Berry>> berries;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.berries;

import java.util.List;

import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("berry-firmness")
public class BerryFirmness implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    private List<NamedAPIResource<Berry>> berries;
    private List<PkmnName> names;
}

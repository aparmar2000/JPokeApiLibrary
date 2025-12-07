package aparmar.pokelibrary.objects.moves;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("move-damage-class")
public class MoveDamageClass implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    private List<PkmnDescription> descriptions;
    private List<NamedAPIResource<PkmnMove>> moves;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.moves;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("move-ailment")
public class MoveAilment implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<PkmnMove>> moves;
    private List<PkmnName> names;
}

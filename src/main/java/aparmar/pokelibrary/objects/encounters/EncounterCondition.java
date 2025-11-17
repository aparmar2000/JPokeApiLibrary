package aparmar.pokelibrary.objects.encounters;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("encounter-condition")
public class EncounterCondition implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<PkmnName> names;
    private List<NamedAPIResource<EncounterConditionValue>> values;
}

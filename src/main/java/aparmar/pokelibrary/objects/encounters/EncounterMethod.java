package aparmar.pokelibrary.objects.encounters;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import lombok.Data;

@Data
@ApiPath("encounter-method")
public class EncounterMethod implements IPaginatedDataObject {
    private int id;
    private String name;
    private int order;
    private List<PkmnName> names;
}

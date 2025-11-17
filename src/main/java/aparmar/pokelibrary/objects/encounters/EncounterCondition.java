package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("encounter-condition")
public class EncounterCondition implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Name> names;
    private List<NamedAPIResource<EncounterConditionValue>> values;
}

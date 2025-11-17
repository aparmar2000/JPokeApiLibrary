package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("encounter-condition-value")
public class EncounterConditionValue implements IPaginatedDataObject {
    private int id;
    private String name;
    private NamedAPIResource<EncounterCondition> condition;
    private List<Name> names;
}

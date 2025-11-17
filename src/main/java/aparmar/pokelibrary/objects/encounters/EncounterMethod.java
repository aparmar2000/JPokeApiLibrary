package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
@ApiPath("encounter-method")
public class EncounterMethod implements IPaginatedDataObject {
    private int id;
    private String name;
    private int order;
    private List<Name> names;
}

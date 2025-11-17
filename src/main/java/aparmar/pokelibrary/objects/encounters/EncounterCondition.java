package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EncounterCondition {
    private int id;
    private String name;
    private List<Name> names;
    private List<NamedAPIResource> values;
}

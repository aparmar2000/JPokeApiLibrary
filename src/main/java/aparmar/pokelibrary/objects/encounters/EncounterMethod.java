package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
public class EncounterMethod {
    private int id;
    private String name;
    private int order;
    private List<Name> names;
}

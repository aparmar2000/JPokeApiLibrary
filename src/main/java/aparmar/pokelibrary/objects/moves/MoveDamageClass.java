package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("move-damage-class")
public class MoveDamageClass implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Description> descriptions;
    private List<NamedAPIResource<Move>> moves;
    private List<Name> names;
}

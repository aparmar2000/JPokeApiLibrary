package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("move-category")
public class MoveCategory implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<Move>> moves;
    private List<Description> descriptions;
}

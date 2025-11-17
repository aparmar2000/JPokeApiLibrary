package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveCategory {
    private int id;
    private String name;
    private List<NamedAPIResource<Move>> moves;
    private List<Description> descriptions;
}

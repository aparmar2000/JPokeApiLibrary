package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
public class MoveBattleStyle {
    private int id;
    private String name;
    private List<Name> names;
}

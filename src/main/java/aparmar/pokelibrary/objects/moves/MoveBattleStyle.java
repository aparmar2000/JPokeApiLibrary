package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
@ApiPath("move-battle-style")
public class MoveBattleStyle implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Name> names;
}

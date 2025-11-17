package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Location {
    private int id;
    private String name;
    private NamedAPIResource region;
    private List<Name> names;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private List<NamedAPIResource> areas;
}

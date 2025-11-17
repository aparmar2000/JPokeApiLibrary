package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveLearnMethod {
    private int id;
    private String name;
    private List<Description> descriptions;
    private List<Name> names;
    @SerializedName("version_groups")
    private List<NamedAPIResource<VersionGroup>> versionGroups;
}

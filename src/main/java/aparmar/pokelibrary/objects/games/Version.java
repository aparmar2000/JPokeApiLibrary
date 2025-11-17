package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("version")
public class Version implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("location")
public class Location implements IPaginatedDataObject {
    private int id;
    private String name;
    private NamedAPIResource<Region> region;
    private List<PkmnName> names;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private List<NamedAPIResource<LocationArea>> areas;
}

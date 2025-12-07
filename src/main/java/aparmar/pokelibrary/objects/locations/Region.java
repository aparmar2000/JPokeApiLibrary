package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.games.Pokedex;
import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("region")
public class Region implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private List<NamedAPIResource<Location>> locations;
    private String name;
    private List<PkmnName> names;
    @SerializedName("main_generation")
    private NamedAPIResource<PkmnGeneration> mainGeneration;
    private List<NamedAPIResource<Pokedex>> pokedexes;
    @SerializedName("version_groups")
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

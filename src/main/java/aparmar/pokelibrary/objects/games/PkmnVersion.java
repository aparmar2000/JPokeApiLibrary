package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("version")
public class PkmnVersion implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    private List<PkmnName> names;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

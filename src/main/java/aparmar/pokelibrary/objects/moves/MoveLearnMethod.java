package aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("move-learn-method")
public class MoveLearnMethod implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<PkmnDescription> descriptions;
    private List<PkmnName> names;
    @SerializedName("version_groups")
    @OptionalField
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

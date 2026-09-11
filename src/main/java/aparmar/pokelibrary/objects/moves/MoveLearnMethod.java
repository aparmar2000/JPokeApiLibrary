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
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("move-learn-method")
public class MoveLearnMethod extends PkmnNamedDataObject implements IPaginatedDataObject {
    private List<PkmnDescription> descriptions;
    private List<PkmnName> names;
    @SerializedName("version_groups")
    @OptionalField
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

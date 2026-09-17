package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("version")
@ToString(callSuper = true)
public class PkmnVersion extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<PkmnName> names;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

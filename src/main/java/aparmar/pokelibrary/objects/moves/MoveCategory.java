package aparmar.pokelibrary.objects.moves;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
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
@ApiPath("move-category")
@ToString(callSuper = true)
public class MoveCategory extends PkmnNamedDataObject implements IPaginatedDataObject {
    private List<NamedAPIResource<PkmnMove>> moves;
    private List<PkmnDescription> descriptions;
}

package aparmar.pokelibrary.objects.encounters;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
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
@ApiPath("encounter-condition-value")
@ToString(callSuper = true)
public class EncounterConditionValue extends PkmnNamedDataObject implements IPaginatedDataObject {
    private NamedAPIResource<EncounterCondition> condition;
    private List<PkmnName> names;
}

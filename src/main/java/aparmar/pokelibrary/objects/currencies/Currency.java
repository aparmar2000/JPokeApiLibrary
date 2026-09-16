package aparmar.pokelibrary.objects.currencies;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("currency")
public class Currency extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<PkmnName> names;
}

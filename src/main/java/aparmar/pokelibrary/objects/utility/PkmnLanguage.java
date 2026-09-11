package aparmar.pokelibrary.objects.utility;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("language")
public class PkmnLanguage extends PkmnNamedDataObject implements IPaginatedDataObject {
    private boolean official;
    private String iso639;
    private String iso3166;
    private List<PkmnName> names;
}

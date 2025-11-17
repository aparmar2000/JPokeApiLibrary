package aparmar.pokelibrary.objects.utility;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import lombok.Data;

@Data
@ApiPath("language")
public class PkmnLanguage implements IPaginatedDataObject {
    private int id;
    private String name;
    private boolean official;
    private String iso639;
    private String iso3166;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.ILangData;
import lombok.Data;

@Data
public class PkmnName implements ILangData {
    private String name;
    private NamedAPIResource<PkmnLanguage> language;
}

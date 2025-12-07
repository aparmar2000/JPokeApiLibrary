package aparmar.pokelibrary.objects.contests;

import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ContestName implements ILangData {
    private String name;
    private String color;
    private NamedAPIResource<PkmnLanguage> language;
}

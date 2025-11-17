package aparmar.pokelibrary.objects.contests;

import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ContestName {
    private String name;
    private String color;
    private NamedAPIResource<PkmnLanguage> language;
}

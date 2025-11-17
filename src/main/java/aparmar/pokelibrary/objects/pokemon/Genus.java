package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class Genus {
    private String genus;
    private NamedAPIResource<PkmnLanguage> language;
}

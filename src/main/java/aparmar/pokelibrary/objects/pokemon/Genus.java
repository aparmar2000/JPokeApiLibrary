package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class Genus implements ILangData {
    private String genus;
    private NamedAPIResource<PkmnLanguage> language;
}

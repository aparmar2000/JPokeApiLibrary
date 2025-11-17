package aparmar.pokelibrary.objects.utility;

import lombok.Data;

@Data
public class PkmnName {
    private String name;
    private NamedAPIResource<PkmnLanguage> language;
}

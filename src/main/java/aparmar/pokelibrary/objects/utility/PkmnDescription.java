package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.ILangData;
import lombok.Data;

@Data
public class PkmnDescription implements ILangData {
	private String description;
	private NamedAPIResource<PkmnLanguage> language;
}

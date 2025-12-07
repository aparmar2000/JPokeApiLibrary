package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.ILangData;
import lombok.Data;

@Data
public class Effect implements ILangData {
	private String effect;
	private NamedAPIResource<PkmnLanguage> language;
}

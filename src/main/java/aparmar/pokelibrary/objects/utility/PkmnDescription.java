package aparmar.pokelibrary.objects.utility;

import lombok.Data;

@Data
public class PkmnDescription {
	private String description;
	private NamedAPIResource<PkmnLanguage> language;
}

package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.games.PkmnVersion;
import lombok.Data;

@Data
public class FlavorText implements ILangData {
    @SerializedName("flavor_text")
    private String flavorText;

    private NamedAPIResource<PkmnLanguage> language;
    private NamedAPIResource<PkmnVersion> version;
}

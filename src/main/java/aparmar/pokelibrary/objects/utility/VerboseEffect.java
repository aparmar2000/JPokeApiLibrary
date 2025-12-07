package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ILangData;
import lombok.Data;

@Data
public class VerboseEffect implements ILangData {
    private String effect;

    @SerializedName("short_effect")
    private String shortEffect;
    
    private NamedAPIResource<PkmnLanguage> language;
}

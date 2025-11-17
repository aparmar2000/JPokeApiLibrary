package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VerboseEffect {
    private String effect;

    @SerializedName("short_effect")
    private String shortEffect;
    
    private NamedAPIResource<PkmnLanguage> language;
}

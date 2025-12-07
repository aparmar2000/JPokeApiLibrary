package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import lombok.Data;

@Data
public class VersionGroupFlavorText implements ILangData {
    private String text;
    private NamedAPIResource<PkmnLanguage> language;
    
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

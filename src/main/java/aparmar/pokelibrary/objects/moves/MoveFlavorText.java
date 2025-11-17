package aparmar.pokelibrary.objects.moves;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class MoveFlavorText {
    @SerializedName("flavor_text")
    private String flavorText;
    private NamedAPIResource<PkmnLanguage> language;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersion;
import lombok.Data;

@Data
public class VersionGameIndex {
    @SerializedName("game_index")
    private int gameIndex;

    private NamedAPIResource<PkmnVersion> version;
}

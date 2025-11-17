package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.berries.BerryFlavor;
import aparmar.pokelibrary.objects.moves.MoveBattleStylePreference;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("nature")
public class Nature implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("decreased_stat")
    private NamedAPIResource<PkmnStat> decreasedStat;
    @SerializedName("increased_stat")
    private NamedAPIResource<PkmnStat> increasedStat;
    @SerializedName("hates_flavor")
    private NamedAPIResource<BerryFlavor> hatesFlavor;
    @SerializedName("likes_flavor")
    private NamedAPIResource<BerryFlavor> likesFlavor;
    @SerializedName("pokeathlon_stat_changes")
    private List<NatureStatChange> pokeathlonStatChanges;
    @SerializedName("move_battle_style_preferences")
    private List<MoveBattleStylePreference> moveBattleStylePreferences;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.berries.BerryFlavor;
import aparmar.pokelibrary.objects.moves.MoveBattleStylePreference;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("nature")
public class Nature extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @SerializedName("decreased_stat")
    @OptionalField
    private NamedAPIResource<PkmnStat> decreasedStat;
    @SerializedName("increased_stat")
    @OptionalField
    private NamedAPIResource<PkmnStat> increasedStat;
    @SerializedName("hates_flavor")
    @OptionalField
    private NamedAPIResource<BerryFlavor> hatesFlavor;
    @SerializedName("likes_flavor")
    @OptionalField
    private NamedAPIResource<BerryFlavor> likesFlavor;
    @SerializedName("pokeathlon_stat_changes")
    private List<NatureStatChange> pokeathlonStatChanges;
    @SerializedName("move_battle_style_preferences")
    private List<MoveBattleStylePreference> moveBattleStylePreferences;
    private List<PkmnName> names;
}

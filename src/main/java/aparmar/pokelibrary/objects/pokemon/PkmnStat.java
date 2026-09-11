package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.moves.MoveDamageClass;
import aparmar.pokelibrary.objects.utility.APIResource;
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
@ApiPath("stat")
public class PkmnStat extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @SerializedName("game_index")
    private int gameIndex;
    @SerializedName("is_battle_only")
    private boolean isBattleOnly;
    @SerializedName("affecting_moves")
    private MoveStatAffectSets affectingMoves;
    @SerializedName("affecting_natures")
    private NatureStatAffectSets affectingNatures;
    @OptionalField
    private List<APIResource<Characteristic>> characteristics;
    @SerializedName("move_damage_class")
    @OptionalField
    private NamedAPIResource<MoveDamageClass> moveDamageClass;
    private List<PkmnName> names;
}

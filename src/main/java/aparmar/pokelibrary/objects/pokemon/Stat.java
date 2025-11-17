package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.moves.MoveDamageClass;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("stat")
public class Stat implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("game_index")
    private int gameIndex;
    @SerializedName("is_battle_only")
    private boolean isBattleOnly;
    @SerializedName("affecting_moves")
    private MoveStatAffectSets affectingMoves;
    @SerializedName("affecting_natures")
    private NatureStatAffectSets affectingNatures;
    private List<APIResource<Characteristic>> characteristics;
    @SerializedName("move_damage_class")
    private NamedAPIResource<MoveDamageClass> moveDamageClass;
    private List<Name> names;
}

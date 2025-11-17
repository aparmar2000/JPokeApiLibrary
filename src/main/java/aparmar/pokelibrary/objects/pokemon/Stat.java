package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Stat {
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
    private List<APIResource> characteristics;
    @SerializedName("move_damage_class")
    private NamedAPIResource moveDamageClass;
    private List<Name> names;
}

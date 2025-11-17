package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.moves.MoveBattleStylePreference;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Nature {
    private int id;
    private String name;
    @SerializedName("decreased_stat")
    private NamedAPIResource decreasedStat;
    @SerializedName("increased_stat")
    private NamedAPIResource increasedStat;
    @SerializedName("hates_flavor")
    private NamedAPIResource hatesFlavor;
    @SerializedName("likes_flavor")
    private NamedAPIResource likesFlavor;
    @SerializedName("pokeathlon_stat_changes")
    private List<NatureStatChange> pokeathlonStatChanges;
    @SerializedName("move_battle_style_preferences")
    private List<MoveBattleStylePreference> moveBattleStylePreferences;
    private List<Name> names;
}

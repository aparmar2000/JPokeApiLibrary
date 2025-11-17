package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.berries.BerryFlavor;
import main.java.aparmar.pokelibrary.objects.moves.MoveBattleStylePreference;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("nature")
public class Nature implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("decreased_stat")
    private NamedAPIResource<Stat> decreasedStat;
    @SerializedName("increased_stat")
    private NamedAPIResource<Stat> increasedStat;
    @SerializedName("hates_flavor")
    private NamedAPIResource<BerryFlavor> hatesFlavor;
    @SerializedName("likes_flavor")
    private NamedAPIResource<BerryFlavor> likesFlavor;
    @SerializedName("pokeathlon_stat_changes")
    private List<NatureStatChange> pokeathlonStatChanges;
    @SerializedName("move_battle_style_preferences")
    private List<MoveBattleStylePreference> moveBattleStylePreferences;
    private List<Name> names;
}

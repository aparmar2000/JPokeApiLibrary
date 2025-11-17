package main.java.aparmar.pokelibrary.objects.moves;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveMetaData {
    private NamedAPIResource<MoveAilment> ailment;
    private NamedAPIResource<MoveCategory> category;
    @SerializedName("min_hits")
    private Integer minHits;
    @SerializedName("max_hits")
    private Integer maxHits;
    @SerializedName("min_turns")
    private Integer minTurns;
    @SerializedName("max_turns")
    private Integer maxTurns;
    private int drain;
    private int healing;
    @SerializedName("crit_rate")
    private int critRate;
    @SerializedName("ailment_chance")
    private int ailmentChance;
    @SerializedName("flinch_chance")
    private int flinchChance;
    @SerializedName("stat_chance")
    private int statChance;
}

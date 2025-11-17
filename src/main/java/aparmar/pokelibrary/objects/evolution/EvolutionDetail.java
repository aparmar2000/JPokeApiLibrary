package main.java.aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EvolutionDetail {
    private NamedAPIResource item;
    private NamedAPIResource trigger;
    private Integer gender;
    @SerializedName("held_item")
    private NamedAPIResource heldItem;
    @SerializedName("known_move")
    private NamedAPIResource knownMove;
    @SerializedName("known_move_type")
    private NamedAPIResource knownMoveType;
    private NamedAPIResource location;
    @SerializedName("min_level")
    private Integer minLevel;
    @SerializedName("min_happiness")
    private Integer minHappiness;
    @SerializedName("min_beauty")
    private Integer minBeauty;
    @SerializedName("min_affection")
    private Integer minAffection;
    @SerializedName("needs_overworld_rain")
    private boolean needsOverworldRain;
    @SerializedName("party_species")
    private NamedAPIResource partySpecies;
    @SerializedName("party_type")
    private NamedAPIResource partyType;
    @SerializedName("relative_physical_stats")
    private Integer relativePhysicalStats;
    @SerializedName("time_of_day")
    private String timeOfDay;
    @SerializedName("trade_species")
    private NamedAPIResource tradeSpecies;
    @SerializedName("turn_upside_down")
    private boolean turnUpsideDown;
}

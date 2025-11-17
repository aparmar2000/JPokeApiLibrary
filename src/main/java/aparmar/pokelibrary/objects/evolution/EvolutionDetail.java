package main.java.aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.items.Item;
import main.java.aparmar.pokelibrary.objects.locations.Location;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.pokemon.Type;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EvolutionDetail {
    private NamedAPIResource<Item> item;
    private NamedAPIResource<EvolutionTrigger> trigger;
    private Integer gender;
    @SerializedName("held_item")
    private NamedAPIResource<Item> heldItem;
    @SerializedName("known_move")
    private NamedAPIResource<Move> knownMove;
    @SerializedName("known_move_type")
    private NamedAPIResource<Type> knownMoveType;
    private NamedAPIResource<Location> location;
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
    private NamedAPIResource<PokemonSpecies> partySpecies;
    @SerializedName("party_type")
    private NamedAPIResource<Type> partyType;
    @SerializedName("relative_physical_stats")
    private Integer relativePhysicalStats;
    @SerializedName("time_of_day")
    private String timeOfDay;
    @SerializedName("trade_species")
    private NamedAPIResource<PokemonSpecies> tradeSpecies;
    @SerializedName("turn_upside_down")
    private boolean turnUpsideDown;
}

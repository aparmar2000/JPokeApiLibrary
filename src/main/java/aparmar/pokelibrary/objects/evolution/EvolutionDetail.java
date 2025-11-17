package aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.locations.Location;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class EvolutionDetail {
    private NamedAPIResource<Item> item;
    private NamedAPIResource<EvolutionTrigger> trigger;
    private Integer gender;
    @SerializedName("held_item")
    private NamedAPIResource<Item> heldItem;
    @SerializedName("known_move")
    private NamedAPIResource<PkmnMove> knownMove;
    @SerializedName("known_move_type")
    private NamedAPIResource<PkmnType> knownMoveType;
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
    private NamedAPIResource<PkmnType> partyType;
    @SerializedName("relative_physical_stats")
    private Integer relativePhysicalStats;
    @SerializedName("time_of_day")
    private String timeOfDay;
    @SerializedName("trade_species")
    private NamedAPIResource<PokemonSpecies> tradeSpecies;
    @SerializedName("turn_upside_down")
    private boolean turnUpsideDown;
}

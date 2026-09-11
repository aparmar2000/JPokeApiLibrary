package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VersionGameIndex;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("pokemon")
public class Pokemon extends PkmnNamedDataObject implements IPaginatedDataObject {
    @SerializedName("base_experience")
    private int baseExperience;
    private int height;
    @SerializedName("is_default")
    private boolean isDefault;
    private int order;
    private int weight;
    private List<PokemonAbility> abilities;
    private List<NamedAPIResource<PokemonForm>> forms;
    @SerializedName("game_indices")
    private List<VersionGameIndex> gameIndices;
    @SerializedName("held_items")
    @OptionalField
    private List<PokemonHeldItem> heldItems;
    @SerializedName("location_area_encounters")
    private String locationAreaEncounters;
    private List<PokemonMove> moves;
    @SerializedName("past_types")
    @OptionalField
    private List<PokemonTypePast> pastTypes;
    @SerializedName("past_abilities")
    @OptionalField
    private List<PokemonAbilityPast> pastAbilities;
    private PokemonSprites sprites;
    private PokemonCries cries;
    private NamedAPIResource<PokemonSpecies> species;
    private List<PokemonStat> stats;
    private List<PokemonType> types;
}

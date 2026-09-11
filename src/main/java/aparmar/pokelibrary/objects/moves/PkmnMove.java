package aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.contests.ContestEffect;
import aparmar.pokelibrary.objects.contests.ContestType;
import aparmar.pokelibrary.objects.contests.SuperContestEffect;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.pokemon.AbilityEffectChange;
import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.MachineVersionDetail;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VerboseEffect;
import lombok.Data;

@Data
@ApiPath("move")
public class PkmnMove implements IPaginatedDataObject {
    private int id;
    private String name;
    @OptionalField
    private Integer accuracy;
    @OptionalField
    @SerializedName("effect_chance")
    private Integer effectChance;
    private int pp;
    private int priority;
    private int power;
    @SerializedName("contest_combos")
    @OptionalField
    private ContestComboSets contestCombos;
    @SerializedName("contest_type")
    @OptionalField
    private NamedAPIResource<ContestType> contestType;
    @SerializedName("contest_effect")
    @OptionalField
    private APIResource<ContestEffect> contestEffect;
    @SerializedName("damage_class")
    private NamedAPIResource<MoveDamageClass> damageClass;
    @SerializedName("effect_entries")
    @OptionalField
    private List<VerboseEffect> effectEntries;
    @SerializedName("effect_changes")
    @OptionalField
    private List<AbilityEffectChange> effectChanges;
    @SerializedName("learned_by_pokemon")
    private List<NamedAPIResource<PokemonSpecies>> learnedByPokemon;
    @SerializedName("flavor_text_entries")
    private List<MoveFlavorText> flavorTextEntries;
    private NamedAPIResource<PkmnGeneration> generation;
    @OptionalField
    private List<MachineVersionDetail> machines;
    @OptionalField
    private MoveMetaData meta;
    private List<PkmnName> names;
    @SerializedName("past_values")
    @OptionalField
    private List<PastMoveStatValues> pastValues;
    @SerializedName("stat_changes")
    @OptionalField
    private List<MoveStatChange> statChanges;
    @SerializedName("super_contest_effect")
    @OptionalField
    private APIResource<SuperContestEffect> superContestEffect;
    private NamedAPIResource<MoveTarget> target;
    private NamedAPIResource<PkmnType> type;
}

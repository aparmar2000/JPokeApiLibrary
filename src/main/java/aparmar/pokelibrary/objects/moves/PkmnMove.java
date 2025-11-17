package aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
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
    private int accuracy;
    @SerializedName("effect_chance")
    private Integer effectChance;
    private int pp;
    private int priority;
    private int power;
    @SerializedName("contest_combos")
    private ContestComboSets contestCombos;
    @SerializedName("contest_type")
    private NamedAPIResource<ContestType> contestType;
    @SerializedName("contest_effect")
    private APIResource<ContestEffect> contestEffect;
    @SerializedName("damage_class")
    private NamedAPIResource<MoveDamageClass> damageClass;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    @SerializedName("effect_changes")
    private List<AbilityEffectChange> effectChanges;
    @SerializedName("learned_by_pokemon")
    private List<NamedAPIResource<PokemonSpecies>> learnedByPokemon;
    @SerializedName("flavor_text_entries")
    private List<MoveFlavorText> flavorTextEntries;
    private NamedAPIResource<PkmnGeneration> generation;
    private List<MachineVersionDetail> machines;
    private MoveMetaData meta;
    private List<PkmnName> names;
    @SerializedName("past_values")
    private List<PastMoveStatValues> pastValues;
    @SerializedName("stat_changes")
    private List<MoveStatChange> statChanges;
    @SerializedName("super_contest_effect")
    private APIResource<SuperContestEffect> superContestEffect;
    private NamedAPIResource<MoveTarget> target;
    private NamedAPIResource<PkmnType> type;
}

package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.contests.ContestEffect;
import main.java.aparmar.pokelibrary.objects.contests.ContestType;
import main.java.aparmar.pokelibrary.objects.contests.SuperContestEffect;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.pokemon.AbilityEffectChange;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.pokemon.Type;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.MachineVersionDetail;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VerboseEffect;

@Data
@ApiPath("move")
public class Move implements IPaginatedDataObject {
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
    private NamedAPIResource<Generation> generation;
    private List<MachineVersionDetail> machines;
    private MoveMetaData meta;
    private List<Name> names;
    @SerializedName("past_values")
    private List<PastMoveStatValues> pastValues;
    @SerializedName("stat_changes")
    private List<MoveStatChange> statChanges;
    @SerializedName("super_contest_effect")
    private APIResource<SuperContestEffect> superContestEffect;
    private NamedAPIResource<MoveTarget> target;
    private NamedAPIResource<Type> type;
}

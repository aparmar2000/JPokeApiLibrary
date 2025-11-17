package aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VerboseEffect;
import lombok.Data;

@Data
public class PastMoveStatValues {
    private int accuracy;
    @SerializedName("effect_chance")
    private int effectChance;
    private int power;
    private int pp;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    private NamedAPIResource<PkmnType> type;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.moves.MoveLearnMethod;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonMoveVersion {
    @SerializedName("move_learn_method")
    private NamedAPIResource<MoveLearnMethod> moveLearnMethod;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
    @SerializedName("level_learned_at")
    private int levelLearnedAt;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonMove {
    private NamedAPIResource<PkmnMove> move;
    @SerializedName("version_group_details")
    private List<PokemonMoveVersion> versionGroupDetails;
}

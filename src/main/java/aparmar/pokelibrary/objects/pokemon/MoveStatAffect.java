package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class MoveStatAffect {
    private int change;
    private NamedAPIResource<PkmnMove> move;
}

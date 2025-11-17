package aparmar.pokelibrary.objects.moves;

import aparmar.pokelibrary.objects.pokemon.PkmnStat;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class MoveStatChange {
    private int change;
    private NamedAPIResource<PkmnStat> stat;
}

package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonType {
    private int slot;
    private NamedAPIResource<PkmnType> type;
}

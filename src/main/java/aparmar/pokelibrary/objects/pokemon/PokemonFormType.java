package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonFormType {
    private int slot;
    private NamedAPIResource<PkmnType> type;
}

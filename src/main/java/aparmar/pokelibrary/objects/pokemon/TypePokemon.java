package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class TypePokemon {
    private int slot;
    private NamedAPIResource<Pokemon> pokemon;
}

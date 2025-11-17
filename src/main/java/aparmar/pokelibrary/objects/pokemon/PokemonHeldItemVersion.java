package aparmar.pokelibrary.objects.pokemon;

import aparmar.pokelibrary.objects.games.PkmnVersion;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonHeldItemVersion {
    private NamedAPIResource<PkmnVersion> version;
    private int rarity;
}

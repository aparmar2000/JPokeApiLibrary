package aparmar.pokelibrary.objects.items;

import aparmar.pokelibrary.objects.games.PkmnVersion;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ItemHolderPokemonVersionDetail {
    private int rarity;
    private NamedAPIResource<PkmnVersion> version;
}

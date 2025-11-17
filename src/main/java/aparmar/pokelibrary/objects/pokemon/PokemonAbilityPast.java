package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonAbilityPast {
    private NamedAPIResource<PkmnGeneration> generation;
    private List<PokemonAbility> abilities;
}

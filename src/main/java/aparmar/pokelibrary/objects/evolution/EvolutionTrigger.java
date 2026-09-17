package aparmar.pokelibrary.objects.evolution;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ToString(callSuper = true)
public class EvolutionTrigger extends PkmnNamedDataObject {
    private List<PkmnName> names;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
}

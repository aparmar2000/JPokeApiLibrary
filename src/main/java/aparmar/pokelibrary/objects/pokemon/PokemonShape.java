package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("pokemon-shape")
public class PokemonShape implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    @SerializedName("awesome_names")
    private List<AwesomeName> awesomeNames;
    private List<PkmnName> names;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
}

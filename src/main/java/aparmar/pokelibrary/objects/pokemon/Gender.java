package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("gender")
public class Gender implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    @SerializedName("pokemon_species_details")
    private List<PokemonSpeciesGender> pokemonSpeciesDetails;
    @SerializedName("required_for_evolution")
    @OptionalField
    private List<NamedAPIResource<PokemonSpecies>> requiredForEvolution;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("gender")
public class Gender extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @SerializedName("pokemon_species_details")
    private List<PokemonSpeciesGender> pokemonSpeciesDetails;
    @SerializedName("required_for_evolution")
    @OptionalField
    private List<NamedAPIResource<PokemonSpecies>> requiredForEvolution;
}

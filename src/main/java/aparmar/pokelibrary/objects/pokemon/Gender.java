package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("gender")
public class Gender implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("pokemon_species_details")
    private List<PokemonSpeciesGender> pokemonSpeciesDetails;
    @SerializedName("required_for_evolution")
    private List<NamedAPIResource<PokemonSpecies>> requiredForEvolution;
}

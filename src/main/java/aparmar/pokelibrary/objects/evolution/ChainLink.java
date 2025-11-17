package aparmar.pokelibrary.objects.evolution;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ChainLink {
    @SerializedName("is_baby")
    private boolean isBaby;
    private NamedAPIResource<PokemonSpecies> species;
    @SerializedName("evolution_details")
    private List<EvolutionDetail> evolutionDetails;
    @SerializedName("evolves_to")
    private List<ChainLink> evolvesTo;
}

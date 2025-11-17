package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VersionEncounterDetail;
import lombok.Data;

@Data
public class PokemonEncounter {
    private NamedAPIResource<PokemonSpecies> pokemon;
    @SerializedName("version_details")
    private List<VersionEncounterDetail> versionDetails;
}

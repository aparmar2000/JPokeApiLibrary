package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
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
@ApiPath("growth-rate")
@ToString(callSuper = true)
public class GrowthRate extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private String formula;
    private List<PkmnDescription> descriptions;
    private List<GrowthRateExperienceLevel> levels;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
}

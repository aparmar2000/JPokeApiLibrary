package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.moves.MoveLearnMethod;
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
@ApiPath("version-group")
@ToString(callSuper = true)
public class PkmnVersionGroup extends PkmnNamedDataObject implements IPaginatedDataObject {
    private int order;
    private NamedAPIResource<PkmnGeneration> generation;
    @SerializedName("move_learn_methods")
    private List<NamedAPIResource<MoveLearnMethod>> moveLearnMethods;
    @OptionalField
    private List<NamedAPIResource<Pokedex>> pokedexes;
    @OptionalField
    private List<NamedAPIResource<Region>> regions;
    private List<NamedAPIResource<PkmnVersion>> versions;
}

package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.moves.MoveLearnMethod;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("version-group")
public class PkmnVersionGroup implements IPaginatedDataObject {
    private int id;
    private String name;
    private int order;
    private NamedAPIResource<PkmnGeneration> generation;
    @SerializedName("move_learn_methods")
    private List<NamedAPIResource<MoveLearnMethod>> moveLearnMethods;
    private List<NamedAPIResource<Pokedex>> pokedexes;
    private List<NamedAPIResource<Region>> regions;
    private List<NamedAPIResource<PkmnVersion>> versions;
}

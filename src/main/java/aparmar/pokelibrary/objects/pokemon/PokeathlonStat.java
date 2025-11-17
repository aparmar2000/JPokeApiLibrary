package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import lombok.Data;

@Data
@ApiPath("pokeathlon-stat")
public class PokeathlonStat implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<PkmnName> names;
    @SerializedName("affecting_natures")
    private NaturePokeathlonStatAffectSets affectingNatures;
}

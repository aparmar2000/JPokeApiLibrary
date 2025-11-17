package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("characteristic")
public class Characteristic implements IPaginatedDataObject {
    private int id;
    @SerializedName("gene_modulo")
    private int geneModulo;
    @SerializedName("possible_values")
    private List<Integer> possibleValues;
    @SerializedName("highest_stat")
    private NamedAPIResource<PkmnStat> highestStat;
    private List<PkmnDescription> descriptions;
}

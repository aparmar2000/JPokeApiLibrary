package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("characteristic")
@ToString(callSuper = true)
public class Characteristic extends PkmnDataObject implements IPaginatedDataObject {
    @SerializedName("gene_modulo")
    private int geneModulo;
    @SerializedName("possible_values")
    private List<Integer> possibleValues;
    @SerializedName("highest_stat")
    private NamedAPIResource<PkmnStat> highestStat;
    private List<PkmnDescription> descriptions;
}

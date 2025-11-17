package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class NaturePokeathlonStatAffect {
    @SerializedName("max_change")
    private int maxChange;
    private NamedAPIResource<Nature> nature;
}

package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class NatureStatChange {
    @SerializedName("max_change")
    private int maxChange;
    @SerializedName("pokeathlon_stat")
    private NamedAPIResource<PokeathlonStat> pokeathlonStat;
}

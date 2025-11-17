package aparmar.pokelibrary.objects.locations;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PalParkEncounterArea {
    @SerializedName("base_score")
    private int baseScore;
    private int rate;
    private NamedAPIResource<LocationArea> area;
}

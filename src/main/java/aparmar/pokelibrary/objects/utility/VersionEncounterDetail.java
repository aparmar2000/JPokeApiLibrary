package aparmar.pokelibrary.objects.utility;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersion;
import lombok.Data;

@Data
public class VersionEncounterDetail {
    private NamedAPIResource<PkmnVersion> version;

    @SerializedName("max_chance")
    private int maxChance;
    
    @SerializedName("encounter_details")
    private List<Encounter> encounterDetails;
}

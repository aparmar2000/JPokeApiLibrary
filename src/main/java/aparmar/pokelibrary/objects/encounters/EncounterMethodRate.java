package aparmar.pokelibrary.objects.encounters;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class EncounterMethodRate {
    @SerializedName("encounter_method")
    private NamedAPIResource<EncounterMethod> encounterMethod;
    @SerializedName("version_details")
    private List<EncounterVersionDetails> versionDetails;
}

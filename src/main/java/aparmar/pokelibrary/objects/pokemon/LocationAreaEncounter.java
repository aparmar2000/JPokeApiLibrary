package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.locations.LocationArea;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VersionEncounterDetail;
import lombok.Data;

@Data
public class LocationAreaEncounter {
    @SerializedName("location_area")
    private NamedAPIResource<LocationArea> locationArea;
    @SerializedName("version_details")
    private List<VersionEncounterDetail> versionDetails;
}

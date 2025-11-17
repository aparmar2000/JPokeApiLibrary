package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.locations.LocationArea;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VersionEncounterDetail;

@Data
public class LocationAreaEncounter {
    @SerializedName("location_area")
    private NamedAPIResource<LocationArea> locationArea;
    @SerializedName("version_details")
    private List<VersionEncounterDetail> versionDetails;
}

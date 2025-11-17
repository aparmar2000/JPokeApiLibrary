package main.java.aparmar.pokelibrary.objects.encounters;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EncounterMethodRate {
    @SerializedName("encounter_method")
    private NamedAPIResource encounterMethod;
    @SerializedName("version_details")
    private List<EncounterVersionDetails> versionDetails;
}

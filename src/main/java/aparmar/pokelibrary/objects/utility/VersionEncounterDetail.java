package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;

@Data
public class VersionEncounterDetail {
    private NamedAPIResource<Version> version;

    @SerializedName("max_chance")
    private int maxChance;
    
    @SerializedName("encounter_details")
    private List<Encounter> encounterDetails;
}

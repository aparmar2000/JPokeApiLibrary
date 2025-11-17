package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VersionEncounterDetail {
    private NamedAPIResource version;

    @SerializedName("max_chance")
    private int maxChance;
    
    @SerializedName("encounter_details")
    private List<Encounter> encounterDetails;
}

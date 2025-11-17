package main.java.aparmar.pokelibrary.objects.locations;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PalParkEncounterArea {
    @SerializedName("base_score")
    private int baseScore;
    private int rate;
    private NamedAPIResource area;
}

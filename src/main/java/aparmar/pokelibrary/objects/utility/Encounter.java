package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.encounters.EncounterConditionValue;
import main.java.aparmar.pokelibrary.objects.encounters.EncounterMethod;

@Data
public class Encounter {
    @SerializedName("min_level")
    private int minLevel;

    @SerializedName("max_level")
    private int maxLevel;

    @SerializedName("condition_values")
    private List<NamedAPIResource<EncounterConditionValue>> conditionValues;

    private int chance;
    private NamedAPIResource<EncounterMethod> method;
}

package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Encounter {
    @SerializedName("min_level")
    private int minLevel;

    @SerializedName("max_level")
    private int maxLevel;

    @SerializedName("condition_values")
    private List<NamedAPIResource> conditionValues;

    private int chance;
    private NamedAPIResource method;
}

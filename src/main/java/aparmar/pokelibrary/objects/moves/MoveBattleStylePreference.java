package main.java.aparmar.pokelibrary.objects.moves;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveBattleStylePreference {
    @SerializedName("low_hp_preference")
    private int lowHpPreference;
    @SerializedName("high_hp_preference")
    private int highHpPreference;
    @SerializedName("move_battle_style")
    private NamedAPIResource moveBattleStyle;
}

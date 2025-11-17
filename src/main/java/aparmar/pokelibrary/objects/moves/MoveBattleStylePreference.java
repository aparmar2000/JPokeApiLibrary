package aparmar.pokelibrary.objects.moves;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class MoveBattleStylePreference {
    @SerializedName("low_hp_preference")
    private int lowHpPreference;
    @SerializedName("high_hp_preference")
    private int highHpPreference;
    @SerializedName("move_battle_style")
    private NamedAPIResource<MoveBattleStyle> moveBattleStyle;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonMoveVersion {
    @SerializedName("move_learn_method")
    private NamedAPIResource moveLearnMethod;
    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
    @SerializedName("level_learned_at")
    private int levelLearnedAt;
}

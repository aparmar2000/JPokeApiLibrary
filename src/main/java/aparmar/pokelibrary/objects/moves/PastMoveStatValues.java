package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VerboseEffect;

@Data
public class PastMoveStatValues {
    private int accuracy;
    @SerializedName("effect_chance")
    private int effectChance;
    private int power;
    private int pp;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    private NamedAPIResource type;
    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
}

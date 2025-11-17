package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.utility.Effect;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class AbilityEffectChange {
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

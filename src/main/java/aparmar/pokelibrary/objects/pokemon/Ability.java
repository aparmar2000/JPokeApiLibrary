package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VerboseEffect;

@Data
@ApiPath("ability")
public class Ability implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("is_main_series")
    private boolean isMainSeries;
    private NamedAPIResource<Generation> generation;
    private List<Name> names;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    @SerializedName("effect_changes")
    private List<AbilityEffectChange> effectChanges;
    @SerializedName("flavor_text_entries")
    private List<AbilityFlavorText> flavorTextEntries;
    private List<AbilityPokemon> pokemon;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VerboseEffect;
import lombok.Data;

@Data
@ApiPath("ability")
public class Ability implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("is_main_series")
    private boolean isMainSeries;
    private NamedAPIResource<PkmnGeneration> generation;
    private List<PkmnName> names;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    @SerializedName("effect_changes")
    private List<AbilityEffectChange> effectChanges;
    @SerializedName("flavor_text_entries")
    private List<AbilityFlavorText> flavorTextEntries;
    private List<AbilityPokemon> pokemon;
}

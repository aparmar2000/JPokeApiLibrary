package aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.evolution.EvolutionChain;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import aparmar.pokelibrary.objects.utility.MachineVersionDetail;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.VerboseEffect;
import aparmar.pokelibrary.objects.utility.VersionGroupFlavorText;
import lombok.Data;

@Data
public class Item {
    private int id;
    private String name;
    private int cost;
    @SerializedName("fling_power")
    private int flingPower;
    @SerializedName("fling_effect")
    private NamedAPIResource<ItemFlingEffect> flingEffect;
    private List<NamedAPIResource<ItemAttribute>> attributes;
    private NamedAPIResource<ItemCategory> category;
    @SerializedName("effect_entries")
    private List<VerboseEffect> effectEntries;
    @SerializedName("flavor_text_entries")
    private List<VersionGroupFlavorText> flavorTextEntries;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private List<PkmnName> names;
    private ItemSprites sprites;
    @SerializedName("held_by_pokemon")
    private List<ItemHolderPokemon> heldByPokemon;
    @SerializedName("baby_trigger_for")
    private APIResource<EvolutionChain> babyTriggerFor;
    private List<MachineVersionDetail> machines;
}

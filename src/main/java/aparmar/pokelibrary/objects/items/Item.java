package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.evolution.EvolutionChain;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import main.java.aparmar.pokelibrary.objects.utility.MachineVersionDetail;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VerboseEffect;
import main.java.aparmar.pokelibrary.objects.utility.VersionGroupFlavorText;

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
    private List<Name> names;
    private ItemSprites sprites;
    @SerializedName("held_by_pokemon")
    private List<ItemHolderPokemon> heldByPokemon;
    @SerializedName("baby_trigger_for")
    private APIResource<EvolutionChain> babyTriggerFor;
    private List<MachineVersionDetail> machines;
}

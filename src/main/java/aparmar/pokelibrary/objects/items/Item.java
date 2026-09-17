package aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.evolution.EvolutionChain;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import aparmar.pokelibrary.objects.utility.MachineVersionDetail;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.VerboseEffect;
import aparmar.pokelibrary.objects.utility.VersionGroupFlavorText;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("item")
@ToString(callSuper = true)
public class Item extends PkmnNamedDataObject implements IPaginatedDataObject {
    @OptionalField
    private List<ItemPrice> prices;
    @SerializedName("fling_power")
    @OptionalField
    private Integer flingPower;
    @SerializedName("fling_effect")
    @OptionalField
    private NamedAPIResource<ItemFlingEffect> flingEffect;
    @OptionalField
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
    @OptionalField
    private List<ItemHolderPokemon> heldByPokemon;
    @SerializedName("baby_trigger_for")
    @OptionalField
    private APIResource<EvolutionChain> babyTriggerFor;
    @OptionalField
    private List<MachineVersionDetail> machines;
}

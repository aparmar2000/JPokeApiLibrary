package aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("evolution-chain")
public class EvolutionChain extends PkmnDataObject implements IPaginatedDataObject {
    @SerializedName("baby_trigger_item")
    @OptionalField
    private NamedAPIResource<Item> babyTriggerItem;
    private ChainLink chain;
}

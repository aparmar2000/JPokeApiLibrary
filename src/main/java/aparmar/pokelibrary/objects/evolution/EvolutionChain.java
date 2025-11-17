package aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("evolution-chain")
public class EvolutionChain implements IPaginatedDataObject {
    private int id;
    @SerializedName("baby_trigger_item")
    private NamedAPIResource<Item> babyTriggerItem;
    private ChainLink chain;
}

package main.java.aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.items.Item;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("evolution-chain")
public class EvolutionChain implements IPaginatedDataObject {
    private int id;
    @SerializedName("baby_trigger_item")
    private NamedAPIResource<Item> babyTriggerItem;
    private ChainLink chain;
}

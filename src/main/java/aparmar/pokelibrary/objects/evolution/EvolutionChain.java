package main.java.aparmar.pokelibrary.objects.evolution;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.items.Item;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EvolutionChain {
    private int id;
    @SerializedName("baby_trigger_item")
    private NamedAPIResource<Item> babyTriggerItem;
    private ChainLink chain;
}

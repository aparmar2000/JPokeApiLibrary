package main.java.aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.items.Item;
import main.java.aparmar.pokelibrary.objects.pokemon.Type;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("berry")
public class Berry implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("growth_time")
    private int growthTime;
    @SerializedName("max_harvest")
    private int maxHarvest;
    @SerializedName("natural_gift_power")
    private int naturalGiftPower;
    private int size;
    private int smoothness;
    @SerializedName("soil_dryness")
    private int soilDryness;
    private NamedAPIResource<BerryFirmness> firmness;
    private List<BerryFlavorMap> flavors;
    private NamedAPIResource<Item> item;
    @SerializedName("natural_gift_type")
    private NamedAPIResource<Type> naturalGiftType;
}

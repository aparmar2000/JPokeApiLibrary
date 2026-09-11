package aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("berry")
public class Berry extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
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
    private NamedAPIResource<PkmnType> naturalGiftType;
}

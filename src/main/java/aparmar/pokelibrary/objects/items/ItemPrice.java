package aparmar.pokelibrary.objects.items;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.currencies.Currency;
import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class ItemPrice {
    private NamedAPIResource<Currency> currency;
    @SerializedName("purchase_price")
    @OptionalField
    private Integer purchasePrice;
    @SerializedName("sell_price")
    @OptionalField
    private Integer sellPrice;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

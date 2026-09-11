package aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.Effect;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
public class ItemFlingEffect extends PkmnNamedDataObject {
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    private List<NamedAPIResource<Item>> items;
}

package aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.Effect;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ItemFlingEffect {
    private int id;
    private String name;
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    private List<NamedAPIResource<Item>> items;
}

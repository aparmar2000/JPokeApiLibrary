package aparmar.pokelibrary.objects.items;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("item-category")
public class ItemCategory implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<Item>> items;
    private List<PkmnName> names;
    private NamedAPIResource<ItemPocket> pocket;
}

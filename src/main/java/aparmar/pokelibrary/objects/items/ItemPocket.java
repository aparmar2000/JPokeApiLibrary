package aparmar.pokelibrary.objects.items;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("item-pocket")
public class ItemPocket implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<ItemCategory>> categories;
    private List<PkmnName> names;
}

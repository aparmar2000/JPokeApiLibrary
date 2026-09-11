package aparmar.pokelibrary.objects.items;

import java.util.List;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("item-category")
public class ItemCategory extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<NamedAPIResource<Item>> items;
    private List<PkmnName> names;
    private NamedAPIResource<ItemPocket> pocket;
}

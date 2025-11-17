package aparmar.pokelibrary.objects.items;

import java.util.List;

import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ItemAttribute {
    private int id;
    private String name;
    private List<NamedAPIResource<Item>> items;
    private List<PkmnName> names;
    private List<PkmnDescription> descriptions;
}

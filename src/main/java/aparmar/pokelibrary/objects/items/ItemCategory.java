package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("item-category")
public class ItemCategory implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<Item>> items;
    private List<Name> names;
    private NamedAPIResource<ItemPocket> pocket;
}

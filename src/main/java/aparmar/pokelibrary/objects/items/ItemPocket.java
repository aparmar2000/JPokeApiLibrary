package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("item-pocket")
public class ItemPocket implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<NamedAPIResource<ItemCategory>> categories;
    private List<Name> names;
}

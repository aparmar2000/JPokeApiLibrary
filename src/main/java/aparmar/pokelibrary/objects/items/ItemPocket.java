package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ItemPocket {
    private int id;
    private String name;
    private List<NamedAPIResource<ItemCategory>> categories;
    private List<Name> names;
}

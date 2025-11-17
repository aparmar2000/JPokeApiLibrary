package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ItemCategory {
    private int id;
    private String name;
    private List<NamedAPIResource> items;
    private List<Name> names;
    private NamedAPIResource pocket;
}

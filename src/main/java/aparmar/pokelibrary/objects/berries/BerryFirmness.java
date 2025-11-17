package main.java.aparmar.pokelibrary.objects.berries;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class BerryFirmness {
    private int id;
    private String name;
    private List<NamedAPIResource> berries;
    private List<Name> names;
}

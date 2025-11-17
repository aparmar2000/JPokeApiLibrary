package main.java.aparmar.pokelibrary.objects.berries;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class FlavorBerryMap {
    private int potency;
    private NamedAPIResource<Berry> berry;
}

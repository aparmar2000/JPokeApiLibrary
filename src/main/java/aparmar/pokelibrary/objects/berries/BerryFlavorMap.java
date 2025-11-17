package main.java.aparmar.pokelibrary.objects.berries;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class BerryFlavorMap {
    private int potency;
    private NamedAPIResource flavor;
}

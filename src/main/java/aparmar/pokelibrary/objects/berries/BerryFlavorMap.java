package aparmar.pokelibrary.objects.berries;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class BerryFlavorMap {
    private int potency;
    private NamedAPIResource<BerryFlavor> flavor;
}

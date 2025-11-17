package aparmar.pokelibrary.objects.berries;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class FlavorBerryMap {
    private int potency;
    private NamedAPIResource<Berry> berry;
}

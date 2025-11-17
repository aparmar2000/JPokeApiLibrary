package aparmar.pokelibrary.objects.berries;

import java.util.List;

import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class BerryFirmness {
    private int id;
    private String name;
    private List<NamedAPIResource<Berry>> berries;
    private List<PkmnName> names;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class NatureStatAffectSets {
    private List<NamedAPIResource<PkmnStat>> increase;
    private List<NamedAPIResource<PkmnStat>> decrease;
}

package aparmar.pokelibrary.objects.encounters;

import aparmar.pokelibrary.objects.games.PkmnVersion;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class EncounterVersionDetails {
    private int rate;
    private NamedAPIResource<PkmnVersion> version;
}

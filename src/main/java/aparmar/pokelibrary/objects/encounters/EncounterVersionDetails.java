package main.java.aparmar.pokelibrary.objects.encounters;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class EncounterVersionDetails {
    private int rate;
    private NamedAPIResource<Version> version;
}

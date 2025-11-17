package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class NatureStatAffectSets {
    private List<NamedAPIResource> increase;
    private List<NamedAPIResource> decrease;
}

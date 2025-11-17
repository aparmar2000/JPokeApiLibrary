package main.java.aparmar.pokelibrary.objects.pokemon;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Language;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Genus {
    private String genus;
    private NamedAPIResource<Language> language;
}

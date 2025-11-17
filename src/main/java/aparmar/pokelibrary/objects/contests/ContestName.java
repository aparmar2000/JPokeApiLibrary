package main.java.aparmar.pokelibrary.objects.contests;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Language;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ContestName {
    private String name;
    private String color;
    private NamedAPIResource<Language> language;
}

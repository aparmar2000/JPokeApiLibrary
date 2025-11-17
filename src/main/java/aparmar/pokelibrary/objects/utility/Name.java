package main.java.aparmar.pokelibrary.objects.utility;

import lombok.Data;

@Data
public class Name {
    private String name;
    private NamedAPIResource<Language> language;
}

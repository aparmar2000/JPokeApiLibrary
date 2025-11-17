package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import lombok.Data;

@Data
public class Language {
    private int id;
    private String name;
    private boolean official;
    private String iso639;
    private String iso3166;
    private List<Name> names;
}

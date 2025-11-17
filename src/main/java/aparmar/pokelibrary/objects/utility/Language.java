package main.java.aparmar.pokelibrary.objects.utility;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;

@Data
@ApiPath("language")
public class Language implements IPaginatedDataObject {
    private int id;
    private String name;
    private boolean official;
    private String iso639;
    private String iso3166;
    private List<Name> names;
}

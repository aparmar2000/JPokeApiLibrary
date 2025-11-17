package main.java.aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class BerryFlavor {
    private int id;
    private String name;
    private List<FlavorBerryMap> berries;
    @SerializedName("contest_type")
    private NamedAPIResource contestType;
    private List<Name> names;
}

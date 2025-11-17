package main.java.aparmar.pokelibrary.objects.berries;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.contests.ContestType;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class BerryFlavor {
    private int id;
    private String name;
    private List<BerryFlavorMap> berries;
    @SerializedName("contest_type")
    private NamedAPIResource<ContestType> contestType;
    private List<Name> names;
}

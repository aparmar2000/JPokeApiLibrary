package main.java.aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.berries.BerryFlavor;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ContestType {
    private int id;
    private String name;
    @SerializedName("berry_flavor")
    private NamedAPIResource<BerryFlavor> berryFlavor;
    private List<ContestName> names;
}

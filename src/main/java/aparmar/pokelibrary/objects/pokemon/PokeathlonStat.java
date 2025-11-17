package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
public class PokeathlonStat {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("affecting_natures")
    private NaturePokeathlonStatAffectSets affectingNatures;
}

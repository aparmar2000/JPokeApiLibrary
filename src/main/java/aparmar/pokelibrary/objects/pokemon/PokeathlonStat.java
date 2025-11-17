package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
@ApiPath("pokeathlon-stat")
public class PokeathlonStat implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("affecting_natures")
    private NaturePokeathlonStatAffectSets affectingNatures;
}

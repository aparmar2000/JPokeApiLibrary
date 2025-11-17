package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class NaturePokeathlonStatAffect {
    @SerializedName("max_change")
    private int maxChange;
    private NamedAPIResource nature;
}

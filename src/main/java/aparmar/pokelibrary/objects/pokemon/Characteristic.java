package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Characteristic {
    private int id;
    @SerializedName("gene_modulo")
    private int geneModulo;
    @SerializedName("possible_values")
    private List<Integer> possibleValues;
    @SerializedName("highest_stat")
    private NamedAPIResource<Stat> highestStat;
    private List<Description> descriptions;
}

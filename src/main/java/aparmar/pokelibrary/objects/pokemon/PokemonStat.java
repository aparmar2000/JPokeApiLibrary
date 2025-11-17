package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonStat {
    private NamedAPIResource stat;
    private int effort;
    @SerializedName("base_stat")
    private int baseStat;
}

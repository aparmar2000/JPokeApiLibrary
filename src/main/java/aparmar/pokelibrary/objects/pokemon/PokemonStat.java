package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonStat {
    private NamedAPIResource<PkmnStat> stat;
    private int effort;
    @SerializedName("base_stat")
    private int baseStat;
}

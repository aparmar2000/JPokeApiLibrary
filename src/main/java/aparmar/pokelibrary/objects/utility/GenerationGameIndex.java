package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnGeneration;
import lombok.Data;

@Data
public class GenerationGameIndex {
    @SerializedName("game_index")
    private int gameIndex;

    private NamedAPIResource<PkmnGeneration> generation;
}

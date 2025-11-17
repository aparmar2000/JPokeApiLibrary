package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonMove {
    private NamedAPIResource<Move> move;
    @SerializedName("version_group_details")
    private List<PokemonMoveVersion> versionGroupDetails;
}

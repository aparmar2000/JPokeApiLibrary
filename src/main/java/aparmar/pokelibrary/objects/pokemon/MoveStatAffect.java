package main.java.aparmar.pokelibrary.objects.pokemon;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveStatAffect {
    private int change;
    private NamedAPIResource<Move> move;
}

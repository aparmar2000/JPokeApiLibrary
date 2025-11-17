package main.java.aparmar.pokelibrary.objects.moves;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.pokemon.Stat;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveStatChange {
    private int change;
    private NamedAPIResource<Stat> stat;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class TypePokemon {
    private int slot;
    private NamedAPIResource pokemon;
}

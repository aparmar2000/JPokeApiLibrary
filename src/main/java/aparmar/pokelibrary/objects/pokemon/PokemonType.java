package main.java.aparmar.pokelibrary.objects.pokemon;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonType {
    private int slot;
    private NamedAPIResource<Type> type;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonHeldItemVersion {
    private NamedAPIResource<Version> version;
    private int rarity;
}

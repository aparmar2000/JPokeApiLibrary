package main.java.aparmar.pokelibrary.objects.items;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ItemHolderPokemonVersionDetail {
    private int rarity;
    private NamedAPIResource<Version> version;
}

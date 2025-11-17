package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonAbilityPast {
    private NamedAPIResource generation;
    private List<PokemonAbility> abilities;
}

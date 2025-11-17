package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.locations.Region;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.pokemon.Ability;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.pokemon.Type;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Generation {
    private int id;
    private String name;
    private List<NamedAPIResource<Ability>> abilities;
    private List<Name> names;
    @SerializedName("main_region")
    private NamedAPIResource<Region> mainRegion;
    private List<NamedAPIResource<Move>> moves;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
    private List<NamedAPIResource<Type>> types;
    @SerializedName("version_groups")
    private List<NamedAPIResource<VersionGroup>> versionGroups;
}

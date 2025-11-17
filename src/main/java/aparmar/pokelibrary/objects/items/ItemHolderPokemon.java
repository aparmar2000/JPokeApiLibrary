package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ItemHolderPokemon {
    private NamedAPIResource<PokemonSpecies> pokemon;
    @SerializedName("version_details")
    private List<ItemHolderPokemonVersionDetail> versionDetails;
}

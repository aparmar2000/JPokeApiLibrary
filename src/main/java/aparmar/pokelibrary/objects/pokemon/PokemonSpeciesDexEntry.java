package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonSpeciesDexEntry {
    @SerializedName("entry_number")
    private int entryNumber;
    private NamedAPIResource pokedex;
}

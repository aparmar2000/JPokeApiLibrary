package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
public class PalParkArea {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("pokemon_encounters")
    private List<PalParkEncounterSpecies> pokemonEncounters;
}

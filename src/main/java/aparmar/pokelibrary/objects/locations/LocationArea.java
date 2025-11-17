package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.encounters.EncounterMethodRate;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class LocationArea {
    private int id;
    private String name;
    @SerializedName("game_index")
    private int gameIndex;
    @SerializedName("encounter_method_rates")
    private List<EncounterMethodRate> encounterMethodRates;
    private NamedAPIResource<Location> location;
    private List<Name> names;
    @SerializedName("pokemon_encounters")
    private List<PokemonEncounter> pokemonEncounters;
}

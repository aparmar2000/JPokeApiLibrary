package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;
import main.java.aparmar.pokelibrary.objects.utility.VersionEncounterDetail;

@Data
public class PokemonEncounter {
    private NamedAPIResource<PokemonSpecies> pokemon;
    @SerializedName("version_details")
    private List<VersionEncounterDetail> versionDetails;
}

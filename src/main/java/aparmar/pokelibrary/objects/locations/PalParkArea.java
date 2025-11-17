package main.java.aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Name;

@Data
@ApiPath("pal-park-area")
public class PalParkArea implements IPaginatedDataObject {
    private int id;
    private String name;
    private List<Name> names;
    @SerializedName("pokemon_encounters")
    private List<PalParkEncounterSpecies> pokemonEncounters;
}

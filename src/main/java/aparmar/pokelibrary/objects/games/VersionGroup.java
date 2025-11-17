package main.java.aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.locations.Region;
import main.java.aparmar.pokelibrary.objects.moves.MoveLearnMethod;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("version-group")
public class VersionGroup implements IPaginatedDataObject {
    private int id;
    private String name;
    private int order;
    private NamedAPIResource<Generation> generation;
    @SerializedName("move_learn_methods")
    private List<NamedAPIResource<MoveLearnMethod>> moveLearnMethods;
    private List<NamedAPIResource<Pokedex>> pokedexes;
    private List<NamedAPIResource<Region>> regions;
    private List<NamedAPIResource<Version>> versions;
}

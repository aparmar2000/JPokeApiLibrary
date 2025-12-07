package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.pokemon.Ability;
import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
@ApiPath("generation")
public class PkmnGeneration implements IPaginatedDataObject, IEnumerablePkmnData {
    private int id;
    private String name;
    private List<NamedAPIResource<Ability>> abilities;
    private List<PkmnName> names;
    @SerializedName("main_region")
    private NamedAPIResource<Region> mainRegion;
    private List<NamedAPIResource<PkmnMove>> moves;
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
    private List<NamedAPIResource<PkmnType>> types;
    @SerializedName("version_groups")
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

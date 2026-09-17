package aparmar.pokelibrary.objects.games;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.locations.Region;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.pokemon.Ability;
import aparmar.pokelibrary.objects.pokemon.PokemonSpecies;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("generation")
@ToString(callSuper = true)
public class PkmnGeneration extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @OptionalField
    private List<NamedAPIResource<Ability>> abilities;
    private List<PkmnName> names;
    @SerializedName("main_region")
    private NamedAPIResource<Region> mainRegion;
    @OptionalField
    private List<NamedAPIResource<PkmnMove>> moves;
    @OptionalField
    @SerializedName("pokemon_species")
    private List<NamedAPIResource<PokemonSpecies>> pokemonSpecies;
    @OptionalField
    private List<NamedAPIResource<PkmnType>> types;
    @SerializedName("version_groups")
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

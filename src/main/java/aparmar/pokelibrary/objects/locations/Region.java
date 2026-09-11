package aparmar.pokelibrary.objects.locations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.games.Pokedex;
import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("region")
public class Region extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    private List<NamedAPIResource<Location>> locations;
    private List<PkmnName> names;
    @SerializedName("main_generation")
    @OptionalField
    private NamedAPIResource<PkmnGeneration> mainGeneration;
    private List<NamedAPIResource<Pokedex>> pokedexes;
    @SerializedName("version_groups")
    private List<NamedAPIResource<PkmnVersionGroup>> versionGroups;
}

package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("pokemon-form")
@ToString(callSuper = true)
public class PokemonForm extends PkmnNamedDataObject {
    private int order;
    @SerializedName("form_order")
    private int formOrder;
    @SerializedName("is_default")
    private boolean isDefault;
    @SerializedName("is_battle_only")
    private boolean isBattleOnly;
    @SerializedName("is_mega")
    private boolean isMega;
    @SerializedName("form_name")
    private String formName;
    private NamedAPIResource<Pokemon> pokemon;
    private List<PokemonFormType> types;
    private PokemonFormSprites sprites;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
    private List<PkmnName> names;
    @SerializedName("form_names")
    private List<PkmnName> formNames;
}

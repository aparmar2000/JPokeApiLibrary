package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class PokemonForm {
    private int id;
    private String name;
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

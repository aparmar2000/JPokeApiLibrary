package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

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
    private NamedAPIResource<VersionGroup> versionGroup;
    private List<Name> names;
    @SerializedName("form_names")
    private List<Name> formNames;
}

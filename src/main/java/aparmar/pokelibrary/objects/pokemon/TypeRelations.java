package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class TypeRelations {
    @SerializedName("no_damage_to")
    private List<NamedAPIResource<PkmnType>> noDamageTo;
    @SerializedName("half_damage_to")
    private List<NamedAPIResource<PkmnType>> halfDamageTo;
    @SerializedName("double_damage_to")
    private List<NamedAPIResource<PkmnType>> doubleDamageTo;
    @SerializedName("no_damage_from")
    private List<NamedAPIResource<PkmnType>> noDamageFrom;
    @SerializedName("half_damage_from")
    private List<NamedAPIResource<PkmnType>> halfDamageFrom;
    @SerializedName("double_damage_from")
    private List<NamedAPIResource<PkmnType>> doubleDamageFrom;
}

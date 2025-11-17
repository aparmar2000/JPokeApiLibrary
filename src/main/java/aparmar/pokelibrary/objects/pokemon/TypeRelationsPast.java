package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class TypeRelationsPast {
    private NamedAPIResource<PkmnGeneration> generation;
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
}

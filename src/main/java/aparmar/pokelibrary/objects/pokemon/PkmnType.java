package aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IEnumerablePkmnData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.moves.MoveDamageClass;
import aparmar.pokelibrary.objects.utility.GenerationGameIndex;
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
@ApiPath("type")
public class PkmnType extends PkmnNamedDataObject implements IPaginatedDataObject, IEnumerablePkmnData {
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
    @SerializedName("past_damage_relations")
    @OptionalField
    private List<TypeRelationsPast> pastDamageRelations;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private NamedAPIResource<PkmnGeneration> generation;
    @SerializedName("move_damage_class")
    private NamedAPIResource<MoveDamageClass> moveDamageClass;
    private List<PkmnName> names;
    private List<TypePokemon> pokemon;
    private List<NamedAPIResource<PkmnMove>> moves;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.moves.MoveDamageClass;
import main.java.aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("type")
public class Type implements IPaginatedDataObject {
    private int id;
    private String name;
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
    @SerializedName("past_damage_relations")
    private List<TypeRelationsPast> pastDamageRelations;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private NamedAPIResource<Generation> generation;
    @SerializedName("move_damage_class")
    private NamedAPIResource<MoveDamageClass> moveDamageClass;
    private List<Name> names;
    private List<TypePokemon> pokemon;
    private List<NamedAPIResource<Move>> moves;
}

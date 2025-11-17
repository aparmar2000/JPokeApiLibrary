package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.GenerationGameIndex;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Type {
    private int id;
    private String name;
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
    @SerializedName("past_damage_relations")
    private List<TypeRelationsPast> pastDamageRelations;
    @SerializedName("game_indices")
    private List<GenerationGameIndex> gameIndices;
    private NamedAPIResource generation;
    @SerializedName("move_damage_class")
    private NamedAPIResource moveDamageClass;
    private List<Name> names;
    private List<TypePokemon> pokemon;
    private List<NamedAPIResource> moves;
}

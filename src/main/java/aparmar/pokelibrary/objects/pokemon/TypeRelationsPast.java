package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class TypeRelationsPast {
    private NamedAPIResource<Generation> generation;
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
}

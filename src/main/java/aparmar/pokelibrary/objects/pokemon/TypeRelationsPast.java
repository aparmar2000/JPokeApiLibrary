package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class TypeRelationsPast {
    private NamedAPIResource generation;
    @SerializedName("damage_relations")
    private TypeRelations damageRelations;
}

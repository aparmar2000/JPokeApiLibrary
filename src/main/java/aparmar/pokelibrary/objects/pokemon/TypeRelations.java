package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class TypeRelations {
    @SerializedName("no_damage_to")
    private List<NamedAPIResource<Type>> noDamageTo;
    @SerializedName("half_damage_to")
    private List<NamedAPIResource<Type>> halfDamageTo;
    @SerializedName("double_damage_to")
    private List<NamedAPIResource<Type>> doubleDamageTo;
    @SerializedName("no_damage_from")
    private List<NamedAPIResource<Type>> noDamageFrom;
    @SerializedName("half_damage_from")
    private List<NamedAPIResource<Type>> halfDamageFrom;
    @SerializedName("double_damage_from")
    private List<NamedAPIResource<Type>> doubleDamageFrom;
}

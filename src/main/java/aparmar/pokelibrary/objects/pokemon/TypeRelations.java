package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class TypeRelations {
    @SerializedName("no_damage_to")
    private List<NamedAPIResource> noDamageTo;
    @SerializedName("half_damage_to")
    private List<NamedAPIResource> halfDamageTo;
    @SerializedName("double_damage_to")
    private List<NamedAPIResource> doubleDamageTo;
    @SerializedName("no_damage_from")
    private List<NamedAPIResource> noDamageFrom;
    @SerializedName("half_damage_from")
    private List<NamedAPIResource> halfDamageFrom;
    @SerializedName("double_damage_from")
    private List<NamedAPIResource> doubleDamageFrom;
}

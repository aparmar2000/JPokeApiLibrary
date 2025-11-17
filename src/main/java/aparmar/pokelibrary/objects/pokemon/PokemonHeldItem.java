package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonHeldItem {
    private NamedAPIResource item;
    @SerializedName("version_details")
    private List<PokemonHeldItemVersion> versionDetails;
}

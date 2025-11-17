package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class AbilityFlavorText {
    @SerializedName("flavor_text")
    private String flavorText;
    private NamedAPIResource language;
    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
}

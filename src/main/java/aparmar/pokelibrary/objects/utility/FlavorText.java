package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;

@Data
public class FlavorText {
    @SerializedName("flavor_text")
    private String flavorText;

    private NamedAPIResource<Language> language;
    private NamedAPIResource<Version> version;
}

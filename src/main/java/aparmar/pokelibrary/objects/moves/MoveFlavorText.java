package main.java.aparmar.pokelibrary.objects.moves;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.utility.Language;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class MoveFlavorText {
    @SerializedName("flavor_text")
    private String flavorText;
    private NamedAPIResource<Language> language;
    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

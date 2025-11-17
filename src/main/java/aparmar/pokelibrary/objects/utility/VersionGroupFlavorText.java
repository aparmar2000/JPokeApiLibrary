package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;

@Data
public class VersionGroupFlavorText {
    private String text;
    private NamedAPIResource<Language> language;
    
    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

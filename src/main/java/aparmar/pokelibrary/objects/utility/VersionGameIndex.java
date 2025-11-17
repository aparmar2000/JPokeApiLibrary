package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.Version;

@Data
public class VersionGameIndex {
    @SerializedName("game_index")
    private int gameIndex;

    private NamedAPIResource<Version> version;
}

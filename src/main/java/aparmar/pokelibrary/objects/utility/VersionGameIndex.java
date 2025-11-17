package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VersionGameIndex {
    @SerializedName("game_index")
    private int gameIndex;

    private NamedAPIResource version;
}

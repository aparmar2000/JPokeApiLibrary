package main.java.aparmar.pokelibrary.objects.machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.items.Item;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Machine {
    private int id;
    private NamedAPIResource<Item> item;
    private NamedAPIResource<Move> move;
    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

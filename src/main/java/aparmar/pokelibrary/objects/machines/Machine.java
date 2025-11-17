package aparmar.pokelibrary.objects.machines;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class Machine {
    private int id;
    private NamedAPIResource<Item> item;
    private NamedAPIResource<PkmnMove> move;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

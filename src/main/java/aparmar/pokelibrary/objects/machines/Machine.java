package aparmar.pokelibrary.objects.machines;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.items.Item;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ToString(callSuper = true)
public class Machine extends PkmnDataObject {
    private NamedAPIResource<Item> item;
    private NamedAPIResource<PkmnMove> move;
    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

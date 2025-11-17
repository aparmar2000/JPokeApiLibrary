package aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.games.PkmnVersionGroup;
import aparmar.pokelibrary.objects.machines.Machine;
import lombok.Data;

@Data
public class MachineVersionDetail {
    private APIResource<Machine> machine;

    @SerializedName("version_group")
    private NamedAPIResource<PkmnVersionGroup> versionGroup;
}

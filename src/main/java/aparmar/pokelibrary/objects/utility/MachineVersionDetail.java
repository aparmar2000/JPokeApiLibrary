package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.games.VersionGroup;
import main.java.aparmar.pokelibrary.objects.machines.Machine;

@Data
public class MachineVersionDetail {
    private APIResource<Machine> machine;

    @SerializedName("version_group")
    private NamedAPIResource<VersionGroup> versionGroup;
}

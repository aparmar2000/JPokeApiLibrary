package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MachineVersionDetail {
    private APIResource machine;

    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
}

package main.java.aparmar.pokelibrary.objects.machines;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class Machine {
    private int id;
    private NamedAPIResource item;
    private NamedAPIResource move;
    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
}

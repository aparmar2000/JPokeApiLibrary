package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VersionGroupFlavorText {
    private String text;
    private NamedAPIResource language;
    
    @SerializedName("version_group")
    private NamedAPIResource versionGroup;
}

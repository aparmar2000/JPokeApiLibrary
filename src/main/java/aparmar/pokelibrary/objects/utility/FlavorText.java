package main.java.aparmar.pokelibrary.objects.utility;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class FlavorText {
    @SerializedName("flavor_text")
    private String flavorText;

    private NamedAPIResource language;
    private NamedAPIResource version;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class AwesomeName {
    @SerializedName("awesome_name")
    private String awesomeName;
    private NamedAPIResource language;
}

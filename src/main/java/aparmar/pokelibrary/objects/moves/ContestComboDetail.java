package main.java.aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ContestComboDetail {
    @SerializedName("use_before")
    private List<NamedAPIResource> useBefore;
    @SerializedName("use_after")
    private List<NamedAPIResource> useAfter;
}

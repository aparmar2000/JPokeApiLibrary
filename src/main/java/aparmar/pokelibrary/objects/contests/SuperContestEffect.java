package main.java.aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.moves.Move;
import main.java.aparmar.pokelibrary.objects.utility.FlavorText;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("super-contest-effect")
public class SuperContestEffect implements IPaginatedDataObject {
    private int id;
    private int appeal;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    private List<NamedAPIResource<Move>> moves;
}

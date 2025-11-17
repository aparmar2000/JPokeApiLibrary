package main.java.aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.utility.Effect;
import main.java.aparmar.pokelibrary.objects.utility.FlavorText;

@Data
@ApiPath("contest-effect")
public class ContestEffect implements IPaginatedDataObject {
    private int id;
    private int appeal;
    private int jam;
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}

package aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.Effect;
import aparmar.pokelibrary.objects.utility.FlavorText;
import lombok.Data;

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

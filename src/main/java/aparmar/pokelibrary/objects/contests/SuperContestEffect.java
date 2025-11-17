package aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.moves.PkmnMove;
import aparmar.pokelibrary.objects.utility.FlavorText;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
@ApiPath("super-contest-effect")
public class SuperContestEffect implements IPaginatedDataObject {
    private int id;
    private int appeal;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    private List<NamedAPIResource<PkmnMove>> moves;
}

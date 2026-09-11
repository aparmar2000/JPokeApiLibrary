package aparmar.pokelibrary.objects.contests;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.utility.Effect;
import aparmar.pokelibrary.objects.utility.FlavorText;
import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("contest-effect")
public class ContestEffect extends PkmnDataObject implements IPaginatedDataObject {
    private int appeal;
    private int jam;
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}

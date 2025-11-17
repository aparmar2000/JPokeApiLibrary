package aparmar.pokelibrary.objects.moves;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class ContestComboDetail {
    @SerializedName("use_before")
    private List<NamedAPIResource<PkmnMove>> useBefore;
    @SerializedName("use_after")
    private List<NamedAPIResource<PkmnMove>> useAfter;
}

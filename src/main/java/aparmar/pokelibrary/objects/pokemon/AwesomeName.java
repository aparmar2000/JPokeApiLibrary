package aparmar.pokelibrary.objects.pokemon;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import lombok.Data;

@Data
public class AwesomeName implements ILangData {
    @SerializedName("awesome_name")
    private String awesomeName;
    private NamedAPIResource<PkmnLanguage> language;
}

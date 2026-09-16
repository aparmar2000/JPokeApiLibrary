package aparmar.pokelibrary.objects.items;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class ItemSprites {
    @SerializedName("default")
    private String defaultSprite;
}

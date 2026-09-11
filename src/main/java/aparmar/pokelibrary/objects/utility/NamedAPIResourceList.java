package aparmar.pokelibrary.objects.utility;

import java.util.List;

import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class NamedAPIResourceList<T extends PkmnNamedDataObject> {
    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource<T>> results;
}

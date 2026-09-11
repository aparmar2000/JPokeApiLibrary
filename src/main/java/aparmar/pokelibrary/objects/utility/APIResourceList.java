package aparmar.pokelibrary.objects.utility;

import java.util.List;

import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public class APIResourceList<T extends PkmnDataObject> {
    private int count;
    private String next;
    private String previous;
    private List<APIResource<T>> results;
}

package aparmar.pokelibrary.objects.utility;

import java.util.List;

import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public abstract class AbstractApiResourceList<T extends PkmnDataObject, R extends APIResource<T>> {
    protected int count;
    protected String next;
    protected String previous;
    protected List<R> results;
}

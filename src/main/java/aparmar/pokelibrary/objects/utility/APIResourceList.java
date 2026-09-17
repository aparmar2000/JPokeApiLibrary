package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.PkmnDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class APIResourceList<T extends PkmnDataObject> extends AbstractApiResourceList<T, APIResource<T>> {
}

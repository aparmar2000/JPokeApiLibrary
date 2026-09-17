package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class NamedAPIResourceList<T extends PkmnNamedDataObject> extends AbstractApiResourceList<T, NamedAPIResource<T>> {
}

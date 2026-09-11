package aparmar.pokelibrary.objects.utility;

import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedAPIResource<T extends PkmnNamedDataObject> extends APIResource<T> {
	private String name;
}

package aparmar.pokelibrary.objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
public abstract class PkmnNamedDataObject extends PkmnDataObject {
	protected String name;
}

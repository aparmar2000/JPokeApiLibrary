package aparmar.pokelibrary.objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ToString(callSuper = true)
public abstract class PkmnNamedDataObject extends PkmnDataObject {
	protected String name;
}

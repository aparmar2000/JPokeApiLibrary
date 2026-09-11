package aparmar.pokelibrary.objects;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.NONE)
public abstract class PkmnDataObject {
	@EqualsAndHashCode.Exclude
	protected transient LoadSource loadSource;
	@EqualsAndHashCode.Exclude
	protected long refreshTimeMs;
	protected int id;
	
	protected Date getRefreshTime() {
		return new Date(refreshTimeMs);
	}
}

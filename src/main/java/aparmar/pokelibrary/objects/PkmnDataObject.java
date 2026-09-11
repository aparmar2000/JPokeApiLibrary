package aparmar.pokelibrary.objects;

import java.util.Date;

import javax.annotation.Nullable;

import aparmar.pokelibrary.utils.ReflectionUtils;
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

	public static <T extends PkmnDataObject> T replicateWithNewSource(T inst, LoadSource newSource, @Nullable Long newRefreshTimeMs) {
		T copy = ReflectionUtils.shallowReplicate(inst);
		
		copy.loadSource = newSource;
		if (newRefreshTimeMs != null) {
			copy.refreshTimeMs = newRefreshTimeMs;
		}
		
		return copy;
	}
}

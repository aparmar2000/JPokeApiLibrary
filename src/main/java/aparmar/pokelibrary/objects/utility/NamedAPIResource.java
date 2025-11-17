package main.java.aparmar.pokelibrary.objects.utility;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedAPIResource<T> extends APIResource<T> {
	private String name;
}

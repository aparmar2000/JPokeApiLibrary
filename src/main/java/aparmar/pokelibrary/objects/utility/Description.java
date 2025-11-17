package main.java.aparmar.pokelibrary.objects.utility;

import lombok.Data;

@Data
public class Description {
	private String description;
	private NamedAPIResource<Language> language;
}

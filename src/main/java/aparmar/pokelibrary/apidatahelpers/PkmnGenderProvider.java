package aparmar.pokelibrary.apidatahelpers;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.pokemon.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
public class PkmnGenderProvider extends PkmnDataProvider<PkmnGenderProvider.PresetGender, Gender> {
	@Getter
	@RequiredArgsConstructor
	public static enum PresetGender implements PkmnDataProvider.INamedEnum {
		MALE("male"),
		FEMALE("female"),
		GENDERLESS("genderless");
		
		private final String name;
	}
	
	public PkmnGenderProvider(PokeApiLibrary pokeApi) {
		super(pokeApi, PresetGender.class, Gender.class);
	}

	@Override
	protected String getStringName(Gender value) {
		return value.getName();
	}
}

package aparmar.pokelibrary.apidatahelpers;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.pokemon.PkmnStat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
public class PkmnStatProvider extends PkmnDataProvider<PkmnStatProvider.PresetStat, PkmnStat> {
	@Getter
	@RequiredArgsConstructor
	public static enum PresetStat implements PkmnDataProvider.INamedEnum {
		HP("hp"),
		ATTACK("attack"),
		DEFENSE("defense"),
		SPECIAL_ATTACK("special-attack"),
		SPECIAL_DEFENSE("special-defense"),
		ACCURACY("accuracy"),
		EVASION("evasion"),
		SPEED("speed");
		
		private final String name;
	}
	
	public PkmnStatProvider(PokeApiLibrary pokeApi) {
		super(pokeApi, PresetStat.class, PkmnStat.class);
	}

	@Override
	protected String getStringName(PkmnStat value) {
		return value.getName();
	}
}

package aparmar.pokelibrary.apidatahelpers;

import java.util.Set;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.pokemon.PkmnType;
import aparmar.pokelibrary.objects.utility.APIResource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.java.Log;

@Log
public class PkmnTypeProvider extends PkmnDataProvider<PkmnTypeProvider.PresetType, PkmnType> {
	@Getter
	@RequiredArgsConstructor
	public static enum PresetType implements PkmnDataProvider.INamedEnum {
		NORMAL("normal"),
		STEEL("steel"),
		SHADOW("shadow"),
		POISON("poison"),
		ELECTRIC("electric"),
		ICE("ice"),
		DRAGON("dragon"),
		FIGHTING("fighting"),
		WATER("water"),
		UNKNOWN("unknown"),
		ROCK("rock"),
		GHOST("ghost"),
		STELLAR("stellar"),
		BUG("bug"),
		GRASS("grass"),
		FLYING("flying"),
		DARK("dark"),
		FIRE("fire"),
		GROUND("ground"),
		PSYCHIC("psychic"),
		FAIRY("fairy");
		
		private final String name;
	}
	
	public PkmnTypeProvider(PokeApiLibrary pokeApi) {
		super(pokeApi, PresetType.class, PkmnType.class);
	}

	@Override
	protected String getStringName(PkmnType value) {
		return value.getName();
	}
	
	public static double calculateEffectiveness(PkmnType attacking, PkmnType defending) {
		val attackDamageRelations = attacking.getDamageRelations();
		if (attackDamageRelations.getNoDamageTo().stream().map(APIResource::get).anyMatch(t->t.equals(defending))) {
			return 0;
		}
		if (attackDamageRelations.getHalfDamageTo().stream().map(APIResource::get).anyMatch(t->t.equals(defending))) {
			return 0.5;
		}
		if (attackDamageRelations.getDoubleDamageTo().stream().map(APIResource::get).anyMatch(t->t.equals(defending))) {
			return 2;
		}
		return 1;
	}
	
	public static double calculateEffectiveness(Set<PkmnType> attacking, Set<PkmnType> defending) {
		return attacking.stream()
				.flatMapToDouble(attackingType -> {
					return defending.stream().mapToDouble(defendingType -> calculateEffectiveness(attackingType, defendingType));
				})
				.reduce(1, (a,b)->a*b);
	}
}

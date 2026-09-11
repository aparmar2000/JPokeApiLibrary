package aparmar.pokelibrary.objects.pokemon;

import java.util.List;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.ILangData;
import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.OptionalField;
import aparmar.pokelibrary.objects.evolution.EvolutionChain;
import aparmar.pokelibrary.objects.games.PkmnGeneration;
import aparmar.pokelibrary.objects.locations.PalParkEncounterArea;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.PkmnDescription;
import aparmar.pokelibrary.objects.utility.PkmnLanguage;
import aparmar.pokelibrary.objects.utility.FlavorText;
import aparmar.pokelibrary.objects.utility.PkmnName;
import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import lombok.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter(value = AccessLevel.NONE)
@ApiPath("pokemon-species")
public class PokemonSpecies extends PkmnNamedDataObject implements IPaginatedDataObject {
    private int order;
    @SerializedName("gender_rate")
    private int genderRate;
    @SerializedName("capture_rate")
    private int captureRate;
    @SerializedName("base_happiness")
    private int baseHappiness;
    @SerializedName("is_baby")
    private boolean isBaby;
    @SerializedName("is_legendary")
    private boolean isLegendary;
    @SerializedName("is_mythical")
    private boolean isMythical;
    @SerializedName("hatch_counter")
    private int hatchCounter;
    @SerializedName("has_gender_differences")
    private boolean hasGenderDifferences;
    @SerializedName("forms_switchable")
    private boolean formsSwitchable;
    @SerializedName("growth_rate")
    private NamedAPIResource<GrowthRate> growthRate;
    @SerializedName("pokedex_numbers")
    private List<PokemonSpeciesDexEntry> pokedexNumbers;
    @SerializedName("egg_groups")
    private List<NamedAPIResource<EggGroup>> eggGroups;
    private NamedAPIResource<PokemonColor> color;
    private NamedAPIResource<PokemonShape> shape;
    @SerializedName("evolves_from_species")
    @OptionalField
    private NamedAPIResource<PokemonSpecies> evolvesFromSpecies;
    @SerializedName("evolution_chain")
    @OptionalField
    private APIResource<EvolutionChain> evolutionChain;
    @OptionalField
    private NamedAPIResource<PokemonHabitat> habitat;
    private NamedAPIResource<PkmnGeneration> generation;
    private List<PkmnName> names;
    @SerializedName("pal_park_encounters")
    @OptionalField
    private List<PalParkEncounterArea> palParkEncounters;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    @SerializedName("form_descriptions")
    @OptionalField
    private List<PkmnDescription> formDescriptions;
    private List<Genus> genera;
    private List<PokemonSpeciesVariety> varieties;
    
    public Optional<PkmnName> tryGetNameByLang(PkmnLanguage language) {
    	return ILangData.tryGetByLang(names, language);
    }
    public Optional<PkmnName> tryGetNameByLangName(String languageName) {
    	return ILangData.tryGetByLangName(names, languageName);
    }
}

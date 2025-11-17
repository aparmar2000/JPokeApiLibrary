package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.ApiPath;
import main.java.aparmar.pokelibrary.objects.IPaginatedDataObject;
import main.java.aparmar.pokelibrary.objects.evolution.EvolutionChain;
import main.java.aparmar.pokelibrary.objects.games.Generation;
import main.java.aparmar.pokelibrary.objects.locations.PalParkEncounterArea;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.FlavorText;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
@ApiPath("pokemon-species")
public class PokemonSpecies implements IPaginatedDataObject {
    private int id;
    private String name;
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
    private NamedAPIResource<PokemonSpecies> evolvesFromSpecies;
    @SerializedName("evolution_chain")
    private APIResource<EvolutionChain> evolutionChain;
    private NamedAPIResource<PokemonHabitat> habitat;
    private NamedAPIResource<Generation> generation;
    private List<Name> names;
    @SerializedName("pal_park_encounters")
    private List<PalParkEncounterArea> palParkEncounters;
    @SerializedName("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
    @SerializedName("form_descriptions")
    private List<Description> formDescriptions;
    private List<Genus> genera;
    private List<PokemonSpeciesVariety> varieties;
}

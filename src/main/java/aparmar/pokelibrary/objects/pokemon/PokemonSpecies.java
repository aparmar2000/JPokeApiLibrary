package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.locations.PalParkEncounterArea;
import main.java.aparmar.pokelibrary.objects.utility.APIResource;
import main.java.aparmar.pokelibrary.objects.utility.Description;
import main.java.aparmar.pokelibrary.objects.utility.FlavorText;
import main.java.aparmar.pokelibrary.objects.utility.Name;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class PokemonSpecies {
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
    private NamedAPIResource growthRate;
    @SerializedName("pokedex_numbers")
    private List<PokemonSpeciesDexEntry> pokedexNumbers;
    @SerializedName("egg_groups")
    private List<NamedAPIResource> eggGroups;
    private NamedAPIResource color;
    private NamedAPIResource shape;
    @SerializedName("evolves_from_species")
    private NamedAPIResource evolvesFromSpecies;
    @SerializedName("evolution_chain")
    private APIResource evolutionChain;
    private NamedAPIResource habitat;
    private NamedAPIResource generation;
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

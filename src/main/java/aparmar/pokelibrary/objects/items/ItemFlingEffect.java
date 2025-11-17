package main.java.aparmar.pokelibrary.objects.items;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import main.java.aparmar.pokelibrary.objects.utility.Effect;
import main.java.aparmar.pokelibrary.objects.utility.NamedAPIResource;

@Data
public class ItemFlingEffect {
    private int id;
    private String name;
    @SerializedName("effect_entries")
    private List<Effect> effectEntries;
    private List<NamedAPIResource> items;
}

package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import lombok.Data;

@Data
public class NaturePokeathlonStatAffectSets {
    private List<NaturePokeathlonStatAffect> increase;
    private List<NaturePokeathlonStatAffect> decrease;
}

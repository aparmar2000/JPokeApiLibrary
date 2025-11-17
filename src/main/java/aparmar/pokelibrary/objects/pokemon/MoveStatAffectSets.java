package main.java.aparmar.pokelibrary.objects.pokemon;

import java.util.List;

import lombok.Data;

@Data
public class MoveStatAffectSets {
    private List<MoveStatAffect> increase;
    private List<MoveStatAffect> decrease;
}

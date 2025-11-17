package main.java.aparmar.pokelibrary.objects;

import lombok.Data;
import main.java.aparmar.pokelibrary.PokeApiLibrary;

@Data
public abstract class DataObject {
	private PokeApiLibrary libInstance;
    private int id;
}

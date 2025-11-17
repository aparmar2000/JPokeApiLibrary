package main.java.aparmar.pokelibrary.objects;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true)
public abstract class DataObject {
    private int id;
}

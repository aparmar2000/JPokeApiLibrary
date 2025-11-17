package aparmar.pokelibrary.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public abstract class DataObject {
    private int id;
}

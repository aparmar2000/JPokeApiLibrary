package aparmar.pokelibrary.objects.utility;

import java.util.List;

import lombok.Data;

@Data
public class NamedAPIResourceList<T> {
    private int count;
    private String next;
    private String previous;
    private List<NamedAPIResource<T>> results;
}

package aparmar.pokelibrary;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import aparmar.pokelibrary.objects.IPaginatedDataObject;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.utility.APIResource;
import aparmar.pokelibrary.objects.utility.APIResourceList;
import aparmar.pokelibrary.utils.HelperConstants;
import lombok.RequiredArgsConstructor;
import lombok.val;
import okhttp3.ResponseBody;

@RequiredArgsConstructor
public class PokeApiPaginationThread<T extends PkmnDataObject & IPaginatedDataObject> extends Thread {
	private final PokeApiLibrary apiLibrary;
	private final Gson gson;
	private final String apiEndpoint;
	private final Class<T> clazz;
	private final int paginationSize;
	
	private final ConcurrentLinkedQueue<T> itemQueue;	
	
	@Override
    public void run() {
		String nextPaginationUrl = HelperConstants.POKE_API_BASE_URL+"/"+apiEndpoint+"?limit="+paginationSize;
		val resourceListTypeToken = TypeToken.getParameterized(APIResourceList.class, clazz);
		
		while (nextPaginationUrl != null && !nextPaginationUrl.isBlank()) {
			val paginationUrl = nextPaginationUrl;
			nextPaginationUrl = null;
			
			try {
				@SuppressWarnings("unchecked")
				final APIResourceList<T> paginationList = (APIResourceList<T>) apiLibrary.sendRequest(paginationUrl, (ResponseBody body) -> {
						return gson.fromJson(body.string(), resourceListTypeToken);
					});
				paginationList.getResults()
					.stream()
					.map(APIResource::get)
					.forEach(itemQueue::offer);
				
				nextPaginationUrl = paginationList.getNext();
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

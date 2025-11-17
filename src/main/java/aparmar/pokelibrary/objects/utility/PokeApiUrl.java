package aparmar.pokelibrary.objects.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import aparmar.pokelibrary.utils.HelperConstants;
import lombok.Value;

@Value
public class PokeApiUrl {
	private static final Pattern URL_PATTERN = Pattern.compile("pokeapi\\.co/api/v2/(\\w+)/(\\w+)(?:/(\\w+))?", Pattern.CASE_INSENSITIVE);
	
	private String endpointId;
	private String id;
	@Nullable
	private String subEndpointId;
	
	public static PokeApiUrl fromUrlString(String url) {
		Matcher urlMatcher = URL_PATTERN.matcher(url);
		if (urlMatcher.find()) {
			return new PokeApiUrl(urlMatcher.group(1), urlMatcher.group(2), urlMatcher.group(3));
		}
		
		throw new IllegalArgumentException(String.format("Failed to parse malformed PokeAPI URL '%s'!", url));
	}
	
	public String getUrl() {		
		return HelperConstants.POKE_API_BASE_URL + getRelativeUrl();
	}
	
	public String getRelativeUrl() {
		String url = "/"+endpointId+"/"+id;
		if (subEndpointId != null) {
			url += "/"+subEndpointId;
		}
		
		return url;
	}
}

package aparmar.pokelibrary.objects.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import aparmar.pokelibrary.objects.ApiPath;
import aparmar.pokelibrary.objects.PkmnDataObject;
import aparmar.pokelibrary.objects.PkmnNamedDataObject;
import aparmar.pokelibrary.utils.HelperConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PokeApiUrl {
	private static final Pattern URL_PATTERN = Pattern.compile("pokeapi\\.co/api/v2/([a-zA-Z0-9_-]+)/([a-zA-Z0-9_-]+)(?:/([a-zA-Z0-9_-]+))?", Pattern.CASE_INSENSITIVE);
	
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
	
	// -- from id

	public static PokeApiUrl fromEndpointAndId(String endpoint, int id) {
		return fromEndpointAndId(endpoint, null, id);
	}
	public static PokeApiUrl fromEndpointAndId(String endpoint, String subEndpoint, int id) {
		return new PokeApiUrl(endpoint, Integer.toString(id), subEndpoint.isBlank() ? null : subEndpoint);
	}
	
	public static PokeApiUrl fromApiPathAnnotationAndId(ApiPath apiPath, int id) {
		return fromEndpointAndId(apiPath.value(), apiPath.subEndpoint(), id);
	}
	
	public static <T extends PkmnDataObject> PokeApiUrl fromClassAndId(Class<T> clazz, int id) {
		final ApiPath apiPathAnnotation = (ApiPath) clazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new IllegalArgumentException(String.format("Cannot construct PokeApiUrl from specified class %s; missing @ApiPath annotation!", clazz.getSimpleName()));
		}
		return fromApiPathAnnotationAndId(apiPathAnnotation, id);
	}
	
	// -- from name

	public static PokeApiUrl fromEndpointAndName(String endpoint, String name) {
		return new PokeApiUrl(endpoint, null, name);
	}
	public static PokeApiUrl fromEndpointAndName(String endpoint, String subEndpoint, String name) {
		return new PokeApiUrl(endpoint, name, subEndpoint.isBlank() ? null : subEndpoint);
	}
	
	public static PokeApiUrl fromApiPathAnnotationAndName(ApiPath apiPath, String name) {
		return fromEndpointAndName(apiPath.value(), apiPath.subEndpoint(), name);
	}
	
	public static <T extends PkmnNamedDataObject> PokeApiUrl fromClassAndName(Class<T> clazz, String name) {
		final ApiPath apiPathAnnotation = (ApiPath) clazz.getAnnotation(ApiPath.class);
		if (apiPathAnnotation == null) {
			throw new IllegalArgumentException(String.format("Cannot construct PokeApiUrl from specified class %s; missing @ApiPath annotation!", clazz.getSimpleName()));
		}
		return fromApiPathAnnotationAndName(apiPathAnnotation, name);
	}
	
	// --
	
	@EqualsAndHashCode.Include(replaces = "subEndpointId")
	private String normalizedSubEndpointId() {
		return subEndpointId == null || subEndpointId.isBlank() ? null : subEndpointId;
	}
	
	public String getUrl() {		
		return HelperConstants.POKE_API_BASE_URL + "/" + getRelativeUrl();
	}
	
	public String getRelativeUrl() {
		String url = endpointId+"/"+id;
		if (subEndpointId != null && !subEndpointId.isBlank()) {
			url += "/"+subEndpointId;
		}
		
		return url;
	}
}

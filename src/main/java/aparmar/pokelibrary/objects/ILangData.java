package aparmar.pokelibrary.objects;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import aparmar.pokelibrary.objects.utility.NamedAPIResource;
import aparmar.pokelibrary.objects.utility.PkmnLanguage;

public interface ILangData {
	public NamedAPIResource<PkmnLanguage> getLanguage();

	public static <T extends ILangData> Optional<T> tryGetByLang(Stream<T> entries, PkmnLanguage langauge) {
		return entries.filter(e->e.getLanguage().get().equals(langauge))
				.findAny();
	}
	public static <T extends ILangData> Optional<T> tryGetByLang(Collection<T> entries, PkmnLanguage langauge) {
		return tryGetByLang(entries.stream(), langauge);
	}
	public static <T extends ILangData> Optional<T> tryGetByLang(Collection<T> entries, NamedAPIResource<PkmnLanguage> langauge) {
		return tryGetByLang(entries, langauge.get());
	}
	public static <T extends ILangData> Optional<T> tryGetByLangName(Stream<T> entries, String langaugeName) {
		return entries.filter(e->e.getLanguage().get().getName().equalsIgnoreCase(langaugeName))
				.findAny();
	}
	public static <T extends ILangData> Optional<T> tryGetByLangName(Collection<T> entries, String langaugeName) {
		return tryGetByLangName(entries.stream(), langaugeName);
	}

	public static <T extends ILangData> Optional<T> tryGetUnwrappedByLang(Stream<NamedAPIResource<T>> entries, PkmnLanguage langauge) {
		return tryGetByLang(entries.map(NamedAPIResource::get), langauge);
	}
	public static <T extends ILangData> Optional<T> tryGetUnwrappedByLang(Collection<NamedAPIResource<T>> entries, PkmnLanguage langauge) {
		return tryGetUnwrappedByLang(entries.stream(), langauge);
	}
	public static <T extends ILangData> Optional<T> tryGetUnwrappedByLang(Collection<NamedAPIResource<T>> entries, NamedAPIResource<PkmnLanguage> langauge) {
		return tryGetUnwrappedByLang(entries, langauge.get());
	}
	public static <T extends ILangData> Optional<T> tryGetUnwrappedByLangName(Stream<NamedAPIResource<T>> entries, String langaugeName) {
		return tryGetByLangName(entries.map(NamedAPIResource::get), langaugeName);
	}
	public static <T extends ILangData> Optional<T> tryGetUnwrappedByLangName(Collection<NamedAPIResource<T>> entries, String langaugeName) {
		return tryGetUnwrappedByLangName(entries.stream(), langaugeName);
	}
}

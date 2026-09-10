package aparmar.pokelibrary.apidatahelpers;

import aparmar.pokelibrary.PokeApiLibrary;
import aparmar.pokelibrary.objects.berries.Berry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
public class PkmnBerryProvider extends PkmnDataProvider<PkmnBerryProvider.PresetBerry, Berry> {
	@Getter
	@RequiredArgsConstructor
	public static enum PresetBerry implements PkmnDataProvider.INamedEnum {
		TAMATO("tamato"),
		OCCA("occa"),
		SALAC("salac"),
		IAPAPA("iapapa"),
		AGUAV("aguav"),
		COBA("coba"),
		LUM("lum"),
		QUALOT("qualot"),
		WIKI("wiki"),
		HABAN("haban"),
		RINDO("rindo"),
		ASPEAR("aspear"),
		RAZZ("razz"),
		SHUCA("shuca"),
		CHARTI("charti"),
		KELPSY("kelpsy"),
		CHOPLE("chople"),
		RABUTA("rabuta"),
		PAMTRE("pamtre"),
		KASIB("kasib"),
		HONDEW("hondew"),
		TANGA("tanga"),
		GREPA("grepa"),
		POMEG("pomeg"),
		CHILAN("chilan"),
		MICLE("micle"),
		NOMEL("nomel"),
		SPELON("spelon"),
		WATMEL("watmel"),
		GANLON("ganlon"),
		COLBUR("colbur"),
		BABIRI("babiri"),
		ENIGMA("enigma"),
		JABOCA("jaboca"),
		PAYAPA("payapa"),
		NANAB("nanab"),
		SITRUS("sitrus"),
		BELUE("belue"),
		PETAYA("petaya"),
		YACHE("yache"),
		BLUK("bluk"),
		APICOT("apicot"),
		MAGO("mago"),
		CUSTAP("custap"),
		CHESTO("chesto"),
		MAGOST("magost"),
		FIGY("figy"),
		STARF("starf"),
		LIECHI("liechi"),
		DURIN("durin"),
		PECHA("pecha"),
		PINAP("pinap"),
		WEPEAR("wepear"),
		CORNN("cornn"),
		ORAN("oran"),
		LEPPA("leppa"),
		ROWAP("rowap"),
		KEBIA("kebia"),
		PASSHO("passho"),
		CHERI("cheri"),
		PERSIM("persim"),
		LANSAT("lansat"),
		WACAN("wacan"),
		RAWST("rawst");
		
		private final String name;
	}
	
	public PkmnBerryProvider(PokeApiLibrary pokeApi) {
		super(pokeApi, PresetBerry.class, Berry.class);
	}

	@Override
	protected String getStringName(Berry value) {
		return value.getName();
	}
}

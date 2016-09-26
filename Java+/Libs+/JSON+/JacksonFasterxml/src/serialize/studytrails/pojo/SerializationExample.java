package serialize.studytrails.pojo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class SerializationExample {

	public static void main(String[] args) throws IOException, ParseException {
		Artist artist = makeArtist();
		Album album = makeAlbum(artist);
		ObjectMapper mapper = initMapper();
		mapper.writeValue(System.out, album);
	}

	private static ObjectMapper initMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
		mapper.setDateFormat(outputFormat);
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
			private static final long serialVersionUID = 1L;

			@Override
			public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
				if (field.getFullName().equals("com.studytrails.json.jackson.Artist#name"))
					return "Artist-Name";
				return super.nameForField(config, field, defaultName);
			}

			@Override
			public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
				if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title"))
					return "Album-Title";
				return super.nameForGetterMethod(config, method, defaultName);
			}
		});
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		return mapper;
	}

	private static Album makeAlbum(Artist artist) {
		Album album = new Album("Kind Of Blue");
		album.setArtist(artist);
		album.setLinks(new String[] { "link1", "link2" });
		List<String> songs = new ArrayList<String>();
		songs.add("So What");
		songs.add("Flamenco Sketches");
		songs.add("Freddie Freeloader");
		album.setSongs(songs);
		album.addMusician("Miles Davis", "Trumpet, Band leader");
		album.addMusician("Julian Adderley", "Alto Saxophone");
		album.addMusician("Paul Chambers", "double bass");
		return album;
	}

	private static Artist makeArtist() throws ParseException {
		Artist artist = new Artist();
		artist.name = "Miles Davis";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		artist.birthDate = format.parse("26-05-1926");
		return artist;
	}
}

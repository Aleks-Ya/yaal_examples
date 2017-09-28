package serialize.pojo;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;

public class ObjectMapperTest {

    @Test
    public void test() throws IOException, ParseException, JSONException {
        Artist artist = makeArtist();
        Album album = makeAlbum(artist);
        ObjectMapper mapper = initMapper();

        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, album);

        String exp = "{ " +
                "'Album-Title': 'Kind Of Blue', " +
                "'links': [ 'link1', 'link2' ], " +
                "'songs': [ 'So What', 'Flamenco Sketches', 'Freddie Freeloader' ], " +
                "'artist': {  'name': 'Miles Davis',  'birthDate': '26 May 1926',  'age': 0 }, " +
                "'musicians': {  'Julian Adderley': 'Alto Saxophone',  'Miles Davis': 'Trumpet, Band leader',  'Paul Chambers': 'double bass' }" +
                "}";
        JSONAssert.assertEquals(writer.toString(), exp, JSONCompareMode.STRICT);
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
        album.setLinks(new String[]{"link1", "link2"});
        List<String> songs = new ArrayList<>();
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

    @SuppressWarnings({"unused", "WeakerAccess"})
    private static class Album {
        private String title;
        private String[] links;
        private List<String> songs = new ArrayList<String>();
        private Artist artist;
        private Map<String, String> musicians = new HashMap<String, String>();

        public Album(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setLinks(String[] links) {
            this.links = links;
        }

        public String[] getLinks() {
            return links;
        }

        public void setSongs(List<String> songs) {
            this.songs = songs;
        }

        public List<String> getSongs() {
            return songs;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }

        public Artist getArtist() {
            return artist;
        }

        public Map<String, String> getMusicians() {
            return Collections.unmodifiableMap(musicians);
        }

        public void addMusician(String key, String value) {
            musicians.put(key, value);
        }
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    private static class Artist {
        public String name;
        public Date birthDate;
        public int age;
        public String homeTown;
        public List<String> awardsWon = new ArrayList<>();
    }
}

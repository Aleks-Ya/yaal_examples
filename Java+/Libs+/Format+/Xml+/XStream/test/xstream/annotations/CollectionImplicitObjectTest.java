package xstream.annotations;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToString;

class CollectionImplicitObjectTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @BeforeEach
    void setUp() {
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.processAnnotations(Person.class);

        List<City> cities = new ArrayList<>();
        cities.add(new City("Moscow"));
        cities.add(new City("London"));
        person = new Person(cities);
        xml = resourceToString("xstream/annotations/CollectionImplicitObjectTest.xml");
    }

    @Test
    void serialize() {
        var actXml = xstream.toXML(person);
        assertThat(actXml).isEqualTo(xml);
    }

    @Test
    void deserialize() {
        var actPerson = (Person) xstream.fromXML(xml);
        assertThat(actPerson).isEqualTo(person);
    }

    @XStreamAlias("participant")
    @SuppressWarnings("unused")
    public static class Person {
        @XStreamImplicit(itemFieldName = "city")
        private List<City> locations;

        public Person(List<City> locations) {
            this.locations = locations;
        }

        public List<City> getLocations() {
            return locations;
        }

        public void setLocations(List<City> locations) {
            this.locations = locations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var person = (Person) o;
            return Objects.equals(locations, person.locations);
        }

        @Override
        public int hashCode() {
            return Objects.hash(locations);
        }
    }

    @SuppressWarnings("unused")
    public static class City {
        private String title;

        public City(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var city = (City) o;
            return Objects.equals(title, city.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title);
        }
    }
}

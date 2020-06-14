package xstream.annotations;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.ResourceUtil.resourceToString;

public class CollectionImplicitObjectTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @Before
    public void setUp() {
        xstream = new XStream();
        xstream.processAnnotations(Person.class);

        List<City> cities = new ArrayList<>();
        cities.add(new City("Moscow"));
        cities.add(new City("London"));
        person = new Person(cities);
        xml = resourceToString("xstream/annotations/CollectionImplicitObjectTest.xml");
    }

    @Test
    public void serialize() {
        String actXml = xstream.toXML(person);
        assertThat(actXml, equalTo(xml));
    }

    @Test
    public void deserialize() {
        Person actPerson = (Person) xstream.fromXML(xml);
        assertThat(actPerson, equalTo(person));
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
            Person person = (Person) o;
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
            City city = (City) o;
            return Objects.equals(title, city.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title);
        }
    }
}

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

public class CollectionImplicitPrimitiveTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @Before
    public void setUp() {
        xstream = new XStream();
        xstream.processAnnotations(Person.class);

        List<String> cities = new ArrayList<>();
        cities.add("Moscow");
        cities.add("London");
        List<Integer> numList = new ArrayList<>();
        numList.add(3);
        numList.add(7);
        person = new Person(cities, numList);
        xml = resourceToString("xstream/annotations/CollectionImplicitPrimitiveTest.xml");
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
        private List<String> locations;

        @XStreamImplicit(itemFieldName = "number")
        private List<Integer> numList;

        public Person(List<String> locations, List<Integer> numList) {
            this.locations = locations;
            this.numList = numList;
        }

        public List<String> getLocations() {
            return locations;
        }

        public void setLocations(List<String> locations) {
            this.locations = locations;
        }

        public List<Integer> getNumList() {
            return numList;
        }

        public void setNumList(List<Integer> numList) {
            this.numList = numList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(locations, person.locations) &&
                    Objects.equals(numList, person.numList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(locations, numList);
        }
    }
}
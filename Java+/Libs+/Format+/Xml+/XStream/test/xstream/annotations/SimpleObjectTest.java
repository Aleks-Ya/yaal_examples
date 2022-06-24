package xstream.annotations;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToString;

class SimpleObjectTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @BeforeEach
    void setUp() {
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.processAnnotations(Person.class);

        person = new Person("Joe", 30, true, "yes", 1500.7);
        xml = resourceToString("xstream/annotations/SimpleObjectTest.xml");
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
        @XStreamAlias("title")
        private String name;

        @XStreamAlias("years")
        private Integer age;

        @XStreamAlias("activityLevel")
        private Double activity;

        private Boolean special;

        @XStreamAsAttribute
        @XStreamAlias("importantClient")
        private String important;

        public Person(String name, Integer age, Boolean special, String important, Double activity) {
            this.name = name;
            this.age = age;
            this.special = special;
            this.important = important;
            this.activity = activity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Boolean getSpecial() {
            return special;
        }

        public void setSpecial(Boolean special) {
            this.special = special;
        }

        public String getImportant() {
            return important;
        }

        public void setImportant(String important) {
            this.important = important;
        }

        public Double getActivity() {
            return activity;
        }

        public void setActivity(Double activity) {
            this.activity = activity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var person = (Person) o;
            return Objects.equals(name, person.name) &&
                    Objects.equals(age, person.age) &&
                    Objects.equals(activity, person.activity) &&
                    Objects.equals(special, person.special) &&
                    Objects.equals(important, person.important);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, activity, special, important);
        }
    }
}

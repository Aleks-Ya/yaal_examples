package xstream.annotations;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.ResourceUtil.resourceToString;

public class NestedObjectTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @BeforeEach
    public void setUp() {
        xstream = new XStream();
        xstream.processAnnotations(new Class[]{Person.class, PhoneNumber.class});

        person = new Person("Joe", "Walnes");
        person.setPhone(new PhoneNumber(123, "1234-456"));
        person.setFax(new PhoneNumber(123, "9999-999"));

        xml = resourceToString("xstream/annotations/NestedObjectTest.xml");
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

    @XStreamAlias("person")
    @SuppressWarnings("unused")
    public static class Person {
        @XStreamAlias("firstname")
        private String firstName;
        @XStreamAlias("lastname")
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        private PhoneNumber phone;

        private PhoneNumber fax;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public PhoneNumber getPhone() {
            return phone;
        }

        public void setPhone(PhoneNumber phone) {
            this.phone = phone;
        }

        public PhoneNumber getFax() {
            return fax;
        }

        public void setFax(PhoneNumber fax) {
            this.fax = fax;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(firstName, person.firstName) &&
                    Objects.equals(lastName, person.lastName) &&
                    Objects.equals(phone, person.phone) &&
                    Objects.equals(fax, person.fax);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName, phone, fax);
        }
    }

    @SuppressWarnings("unused")
    public static class PhoneNumber {
        private int code;
        private String number;

        public PhoneNumber(int code, String number) {
            this.code = code;
            this.number = number;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PhoneNumber that = (PhoneNumber) o;
            return code == that.code &&
                    Objects.equals(number, that.number);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, number);
        }
    }

}

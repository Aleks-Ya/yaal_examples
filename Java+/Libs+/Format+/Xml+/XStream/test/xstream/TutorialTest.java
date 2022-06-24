package xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToString;

/**
 * Source: https://x-stream.github.io/tutorial.html
 */
class TutorialTest {
    private XStream xstream;
    private Person person;
    private String xml;

    @BeforeEach
    void setUp() {
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.alias("person", Person.class);
        xstream.alias("phonenumber", PhoneNumber.class);

        person = new Person("Joe", "Walnes");
        person.setPhone(new PhoneNumber(123, "1234-456"));
        person.setFax(new PhoneNumber(123, "9999-999"));

        xml = resourceToString("xstream/TutorialTest.xml");
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

    @SuppressWarnings("unused")
    public static class Person {
        private String firstname;
        private String lastname;
        private PhoneNumber phone;
        private PhoneNumber fax;

        public Person(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
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
            var person = (Person) o;
            return Objects.equals(firstname, person.firstname) &&
                    Objects.equals(lastname, person.lastname) &&
                    Objects.equals(phone, person.phone) &&
                    Objects.equals(fax, person.fax);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstname, lastname, phone, fax);
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
            var that = (PhoneNumber) o;
            return code == that.code &&
                    Objects.equals(number, that.number);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, number);
        }
    }
}

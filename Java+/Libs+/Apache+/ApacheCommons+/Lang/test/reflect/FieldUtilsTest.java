package reflect;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldUtilsTest {

    @Test
    void readPrivateField() throws IllegalAccessException {
        var expName = "John";
        var child = new Person(expName);
        var actName = (String) FieldUtils.readDeclaredField(child, "name", true);
        assertThat(actName).isEqualTo(expName);
    }

    @Test
    void readPrivateStaticField() throws IllegalAccessException {
        var city = (String) FieldUtils.readDeclaredStaticField(Person.class, "CITY", true);
        assertThat(city).isEqualTo("Moscow");
    }

    @Test
    void getProtectedFieldFromSuperClass() throws IllegalAccessException {
        var expName = "John";
        var child = new ChildClass(expName);
        var actName = (String) FieldUtils.readField(child, "name", true);
        assertThat(actName).isEqualTo(expName);
    }

    @SuppressWarnings("unused")
    private static class Person {
        private static final String CITY = "Moscow";
        private final String name;

        private Person(String name) {
            this.name = name;
        }
    }

    private abstract static class SuperClass {
        protected final String name;

        protected SuperClass(String name) {
            this.name = name;
        }
    }

    private static class ChildClass extends SuperClass {

        private ChildClass(String name) {
            super(name);
        }
    }
}

package reflect;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FieldUtilsTest {

    @Test
    void readPrivateField() throws IllegalAccessException {
        var expName = "John";
        var child = new Person(expName);
        var actName = (String) FieldUtils.readDeclaredField(child, "name", true);
        assertThat(actName, equalTo(expName));
    }

    private static class Person {
        private final String name;

        private Person(String name) {
            this.name = name;
        }
    }

    @Test
    void getProtectedFieldFromSuperClass() throws IllegalAccessException {
        var expName = "John";
        var child = new ChildClass(expName);
        var actName = (String) FieldUtils.readField(child, "name", true);
        assertThat(actName, equalTo(expName));
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

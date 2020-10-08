package reflect;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FieldUtilsTest {

    @Test
    public void readPrivateField() throws IllegalAccessException {
        String expName = "John";
        Person child = new Person(expName);
        String actName = (String) FieldUtils.readDeclaredField(child, "name", true);
        assertThat(actName, equalTo(expName));
    }

    private static class Person {
        private final String name;

        private Person(String name) {
            this.name = name;
        }
    }

    @Test
    public void getProtectedFieldFromSuperClass() throws IllegalAccessException {
        String expName = "John";
        ChildClass child = new ChildClass(expName);
        String actName = (String) FieldUtils.readField(child, "name", true);
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

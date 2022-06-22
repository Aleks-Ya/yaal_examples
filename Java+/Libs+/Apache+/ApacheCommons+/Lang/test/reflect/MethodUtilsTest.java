package reflect;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

class MethodUtilsTest {

    @Test
    void invokePrivateMethodNoArgs() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var person = new Person();
        var name = (String) MethodUtils.invokeMethod(person, true, "getPerson");
        assertThat(name).isEqualTo("John");
    }

    @Test
    @Disabled("Not work")
    void invokePrivateStaticMethodNoArgs() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var method = "getCompany";
        Person.class.getDeclaredMethod(method).setAccessible(true);
        var company = (String) MethodUtils.invokeStaticMethod(Person.class, method);
        assertThat(company).isEqualTo("World Inc.");
    }

    @Test
    void invokePrivateMethodWithArgs() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var person = new Person();
        var greeting = (String) MethodUtils.invokeMethod(person, true, "getGreeting", "John");
        assertThat(greeting).isEqualTo("Hello, John");
    }

    @SuppressWarnings("unused")
    private static class Person {
        private String getPerson() {
            return "John";
        }

        private String getGreeting(String person) {
            return "Hello, " + person;
        }

        private static String getCompany() {
            return "World Inc.";
        }
    }
}

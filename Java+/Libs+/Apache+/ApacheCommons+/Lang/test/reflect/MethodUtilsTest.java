package reflect;

import org.apache.commons.lang3.reflect.MethodUtils;
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
    }
}

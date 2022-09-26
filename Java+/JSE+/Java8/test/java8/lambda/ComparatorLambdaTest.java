package java8.lambda;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование лямбды для реализации интерфейса Comparator.
 */
class ComparatorLambdaTest {

    @Test
    void comparator() {
        Person john = new Person("John");
        Person mary = new Person("Mary");
        Person ira = new Person("Ira");
        List<Person> persons = Arrays.asList(mary, john, ira);

        persons.sort((p1, p2) -> p1.name.compareTo(p2.name));

        assertThat(persons).isEqualTo(Arrays.asList(ira, john, mary));
    }

    private static class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }
    }
}

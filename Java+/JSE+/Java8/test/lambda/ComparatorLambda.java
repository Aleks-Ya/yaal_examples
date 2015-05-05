package lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Использование лямбды для реализации интерфейса Comparator.
 */
public class ComparatorLambda {

    @Test
    public void comparator() {
        Person john = new Person("John");
        Person mary = new Person("Mary");
        Person ira = new Person("Ira");
        List<Person> persons = Arrays.asList(mary, john, ira);

        persons.sort((p1, p2) -> p1.name.compareTo(p2.name));

        assertEquals(Arrays.asList(ira, john, mary), persons);
    }

    private class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }
    }
}

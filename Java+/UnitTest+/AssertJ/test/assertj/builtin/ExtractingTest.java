package assertj.builtin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Extract fields of tested object.
 */
@SuppressWarnings("unused")
class ExtractingTest {
    private static final Car car1 = new Car("BMW", 2020);
    private static final Person person1 = new Person("John", car1);

    private static final Car car2 = new Car("Mercedes", 2019);
    private static final Person person2 = new Person("Mike", car2);

    private static final List<Person> persons = List.of(person1, person2);


    @Test
    void extractSeveralFields() {
        assertThat(persons).extracting("name", "car.model", "car.year")
                .contains(tuple("John", "BMW", 2020),
                        tuple("Mike", "Mercedes", 2019));
    }

    @Test
    void extractSeveralFieldsFlat() {
        assertThat(persons).flatExtracting("name", "car.model", "car.year")
                .contains("John", "BMW", 2020, "Mike", "Mercedes", 2019);
    }

    @Test
    void map() {
        assertThat(persons).map(person -> person.getCar().getModel()).containsOnly("BMW", "Mercedes");
    }

    static class Person {
        private final String name;
        private final Car car;

        Person(String name, Car car) {
            this.name = name;
            this.car = car;
        }

        public String getName() {
            return name;
        }

        public Car getCar() {
            return car;
        }
    }

    static class Car {
        private final String model;
        private final Integer year;

        Car(String model, Integer year) {
            this.model = model;
            this.year = year;
        }

        public String getModel() {
            return model;
        }

        public Integer getYear() {
            return year;
        }
    }
}

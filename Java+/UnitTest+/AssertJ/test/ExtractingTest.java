import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Extract fields of tested object.
 */
@SuppressWarnings("unused")
class ExtractingTest {
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

    @Test
    void extractSeveralFields() {
        var car1 = new Car("BMW", 2020);
        var person1 = new Person("John", car1);

        var car2 = new Car("Mercedes", 2019);
        var person2 = new Person("Mike", car2);

        var persons = List.of(person1, person2);

        assertThat(persons).extracting("name", "car.model", "car.year")
                .contains(tuple("John", "BMW", 2020),
                        tuple("Mike", "Mercedes", 2019));
    }
}

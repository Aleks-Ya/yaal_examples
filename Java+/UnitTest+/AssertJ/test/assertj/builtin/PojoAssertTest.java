package assertj.builtin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unused")
class PojoAssertTest {
    private static final Car car = new Car("BMW", 2020);
    private static final Person person = new Person("John", true, car);

    @Test
    void extractFieldsStr() {
        assertThat(person).extracting("name", "married", "car.model", "car.year")
                .containsExactly("John", true, "BMW", 2020);
    }

    @Test
    void extractFieldsFunc() {
        assertThat(person).extracting(Person::getName, Person::isMarried, p -> p.getCar().getModel(), p -> p.getCar().getYear())
                .containsExactly("John", true, "BMW", 2020);
        assertThat(person).extracting(Person::isMarried).isEqualTo(true);
        assertThat(person).extracting(Person::getCar).extracting(Car::getModel).isEqualTo("BMW");
    }

    @Test
    void satisfies() {
        assertThat(person).satisfies(person -> {
            assertThat(person.getName()).isEqualTo("John");
            assertThat(person.isMarried()).isTrue();
            assertThat(person.getCar()).satisfies(car -> {
                assertThat(car.getModel()).isEqualTo("BMW");
                assertThat(car.getYear()).isEqualTo(2020);
            });
        });
    }

    @Test
    void hasFieldOrPropertyWithValue() {
        assertThat(person)
                .hasFieldOrPropertyWithValue("name", "John")
                .hasFieldOrPropertyWithValue("married", true)
                .hasFieldOrPropertyWithValue("car.model", "BMW")
                .hasFieldOrPropertyWithValue("car.year", 2020);
    }

    @Test
    void returns() {
        assertThat(person)
                .returns("John", Person::getName)
                .returns(true, Person::isMarried)
                .returns("BMW", p -> p.getCar().getModel())
                .returns(2020, p -> p.getCar().getYear());
    }

    static class Person {
        private final String name;
        private final boolean married;
        private final Car car;

        Person(String name, boolean married, Car car) {
            this.name = name;
            this.married = married;
            this.car = car;
        }

        public String getName() {
            return name;
        }

        public boolean isMarried() {
            return married;
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

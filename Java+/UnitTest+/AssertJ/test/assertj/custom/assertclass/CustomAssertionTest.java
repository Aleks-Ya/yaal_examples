package assertj.custom.assertclass;

import org.junit.jupiter.api.Test;

import static assertj.custom.assertclass.CarAssert.assertThat;
import static assertj.custom.assertclass.PersonAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomAssertionTest {

    @Test
    void custom() {
        var person = new Person("John", 30, new Car[]{
                new Car("BMW", 2020),
                new Car("Audi", 2025)
        });
        assertThat(person)
                .hasName("John")
                .isAdult()
                .ageIsPositive()
                .hasCars(
                        car -> assertThat(car).hasModel("BMW").hasYear(2020),
                        car -> assertThat(car).hasModel("Audi").hasYear(2025)
                );
    }

    @Test
    void useBuiltinAssertionsWithinCustomAssertion() {
        assertThatThrownBy(() -> assertThat(new Person("John", -1, new Car[]{})).ageIsPositive())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Age must be positive: actual=-1");
    }

}

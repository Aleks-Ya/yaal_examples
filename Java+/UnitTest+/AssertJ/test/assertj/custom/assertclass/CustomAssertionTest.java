package assertj.custom.assertclass;

import org.junit.jupiter.api.Test;

import static assertj.custom.assertclass.PersonAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomAssertionTest {
    @Test
    void custom() {
        var name = "John";
        var person = new Person(name, 30);
        assertThat(person).hasName(name).isAdult().ageIsPositive();
    }

    @Test
    void useBuiltinAssertionsWithinCustomAssertion() {
        assertThatThrownBy(() -> assertThat(new Person("John", -1)).ageIsPositive())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Age must be positive: actual=-1");
    }
}

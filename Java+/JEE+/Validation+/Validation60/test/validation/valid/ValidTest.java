package validation.valid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static validation.Helper.assertViolation;
import static validation.Helper.VALIDATOR;
import static validation.Helper.violation;

/**
 * Use @{@link javax.validation.Valid} annotation.
 */
class ValidTest {
    @Test
    void validObject() {
        var car = new Car("BMW", 2020);
        var object = new Person("John", "Smith", "john@mail.com", car);
        var violations = VALIDATOR.validate(object);
        assertThat(violations).isEmpty();
    }

    @Test
    void invalidObject() {
        var car = new Car("BMW", null);
        var object = new Person(null, "", "email of john", car);
        var violations = VALIDATOR.validate(object);
        assertViolation(violations,
                violation(Person.class, "firstName", "must not be empty"),
                violation(Person.class, "secondName", "must not be empty"),
                violation(Person.class, "car.year", "must not be null"),
                violation(Person.class, "email", "must be a well-formed email address")
        );
    }

}

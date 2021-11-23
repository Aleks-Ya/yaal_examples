package valid;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static valid.AssertViolation.assertViolation;

class ConstraintTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void notNull() {
        var object = new NotNullConstraint();
        object.comply = "not null value";
        var constraintViolations = List.copyOf(validator.validate(object));
        assertThat(constraintViolations).hasSize(1);
        assertViolation(constraintViolations.get(0), NotNullConstraint.class, "violate", "must not be null");
    }

    @Test
    void notEmpty() {
        var object = new NotEmptyConstraint();
        object.violateEmpty = "";
        object.comply = "not null value";
        var constraintViolations = List.copyOf(validator.validate(object));
        assertThat(constraintViolations).hasSize(2);
        assertViolation(constraintViolations.get(0), NotEmptyConstraint.class, "violateEmpty", "must not be empty");
        assertViolation(constraintViolations.get(1), NotEmptyConstraint.class, "violateNull", "must not be empty");
    }
}

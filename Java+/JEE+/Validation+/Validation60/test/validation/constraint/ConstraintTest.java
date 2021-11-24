package validation.constraint;

import org.junit.jupiter.api.Test;

import static validation.Helper.VALIDATOR;
import static validation.Helper.assertViolation;
import static validation.Helper.violation;

class ConstraintTest {

    @Test
    void notNull() {
        var object = new NotNullConstraint();
        object.comply = "not null value";
        var violations = VALIDATOR.validate(object);
        assertViolation(violations,
                violation(NotNullConstraint.class, "violate", "must not be null"));
    }

    @Test
    void notEmpty() {
        var object = new NotEmptyConstraint();
        object.violateEmpty = "";
        object.comply = "not null value";
        var violations = VALIDATOR.validate(object);
        assertViolation(violations,
                violation(NotEmptyConstraint.class, "violateEmpty", "must not be empty"),
                violation(NotEmptyConstraint.class, "violateNull", "must not be empty"));
    }
}

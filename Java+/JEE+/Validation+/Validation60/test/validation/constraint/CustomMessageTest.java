package validation.constraint;

import org.junit.jupiter.api.Test;

import static validation.Helper.VALIDATOR;
import static validation.Helper.assertViolation;
import static validation.Helper.violation;
import static validation.constraint.CustomMessage.EMPTY_NAME_MESSAGE;

class CustomMessageTest {
    @Test
    void validate() {
        var object = new CustomMessage();
        var violations = VALIDATOR.validate(object);
        assertViolation(violations, violation(CustomMessage.class, "name", EMPTY_NAME_MESSAGE));
    }
}

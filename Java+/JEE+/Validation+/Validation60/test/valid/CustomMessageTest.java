package valid;

import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static valid.AssertViolation.assertViolation;
import static valid.CustomMessage.EMPTY_NAME_MESSAGE;

class CustomMessageTest {
    @Test
    void validate() {
        var object = new CustomMessage();
        var vf = Validation.buildDefaultValidatorFactory();
        var validator = vf.getValidator();
        var constraintViolations = List.copyOf(validator.validate(object));
        assertThat(constraintViolations).hasSize(1);
        assertViolation(constraintViolations.get(0), CustomMessage.class, "name", EMPTY_NAME_MESSAGE);
    }
}

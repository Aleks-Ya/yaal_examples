package validation;

import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static validation.UnderValidation.EMPTY_NAME_MESSAGE;

class ValidationTest {
    @Test
    void validate() {
        var object = new UnderValidation();
        var vf = Validation.buildDefaultValidatorFactory();
        var validator = vf.getValidator();
        var constraintViolations = validator.validate(object);
        assertThat(constraintViolations).hasSize(1);
        var violation0 = List.copyOf(constraintViolations).get(0);
        assertThat(violation0.getMessage()).isEqualTo(EMPTY_NAME_MESSAGE);
    }
}

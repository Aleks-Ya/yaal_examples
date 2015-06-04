import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Example {
    @Test
    public void testName() {
        UderValidation object = new UderValidation();
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        System.out.println(constraintViolations);

    }
}

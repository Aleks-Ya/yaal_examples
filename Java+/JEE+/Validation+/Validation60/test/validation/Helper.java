package validation;

import org.assertj.core.groups.Tuple;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class Helper {
    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void assertViolation(Collection<? extends ConstraintViolation<?>> violations, Tuple... expFieldValues) {
        assertThat(violations)
                .extracting("rootBeanClass", "propertyPath", "interpolatedMessage")
                .containsExactlyInAnyOrder(expFieldValues);
    }

    public static Tuple violation(Class<?> clazz, String propertyPath, String interpolatedMessage) {
        return tuple(clazz, PathImpl.createPathFromString(propertyPath), interpolatedMessage);
    }
}

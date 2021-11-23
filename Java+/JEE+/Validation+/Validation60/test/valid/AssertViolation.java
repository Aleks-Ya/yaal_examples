package valid;

import javax.validation.ConstraintViolation;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertViolation {
    public static void assertViolation(ConstraintViolation<?> violation, Class<?> clazz,
                                       String propertyPath,
                                       String expMessage) {
        assertThat(violation.getRootBean().getClass()).isEqualTo(clazz);
        assertThat(violation.getPropertyPath().toString()).isEqualTo(propertyPath);
        assertThat(violation.getMessage()).isEqualTo(expMessage);
    }
}

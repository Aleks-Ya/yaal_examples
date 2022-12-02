package lang.inheritance.linkage.override.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Нет ограничений на непроверяемые исключения, бросаемые переопределяемым методом.
 */
class UncheckedTest {

    @Test
    void main() {
        assertThat(new Child().makeString()).isEqualTo("Child");
    }

    private static class Parent {
        String makeString() throws NullPointerException {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        @Override
        String makeString() throws IllegalArgumentException {
            return "Child";
        }
    }
}
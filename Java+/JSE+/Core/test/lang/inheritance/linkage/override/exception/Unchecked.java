package lang.inheritance.linkage.override.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Нет ограничений на непроверяемые исключения, бросаемые переопределяемым методом.
 */
class Unchecked {

    @Test
    void main() {
        assertEquals("Child", new Child().makeString());
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
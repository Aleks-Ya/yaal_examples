package lang.inheritance.linkage.override.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Декларируемые исключения должны быть подклассами первоначального.
 */
class ThrowsTypeTest {
    @Test
    void exception() {
        System.out.println(new Child().makeString());
    }


    private static class Parent {
        String makeString() throws IOException, ReflectiveOperationException {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        @Override
        String makeString() {
            return "Child";
        }
    }
}
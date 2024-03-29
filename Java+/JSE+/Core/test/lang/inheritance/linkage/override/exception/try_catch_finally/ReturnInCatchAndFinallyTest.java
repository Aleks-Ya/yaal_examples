package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * What happens if both a catch and a finally block define return statements?
 * <p/>
 * If both catch and finally blocks define return statements, the calling method
 * will receive the value from the finally block.
 */
class ReturnInCatchAndFinallyTest {
    @Test
    void main() {
        System.out.println(method());
    }

    private String method() {
        try {
            throw new Exception();
        } catch (Exception e) {
            return "catch";
        } finally {
            return "finally";
        }
    }
}
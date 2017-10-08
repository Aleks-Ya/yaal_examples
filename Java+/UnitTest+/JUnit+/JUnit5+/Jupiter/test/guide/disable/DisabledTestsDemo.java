package guide.disable;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Source: http://junit.org/junit5/docs/current/user-guide/#writing-tests-disabling
 */
class DisabledTestsDemo {

    @Disabled
    @Test
    void testWillBeSkipped() {
    }

    @Test
    void testWillBeExecuted() {
    }
}

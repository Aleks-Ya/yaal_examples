package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * Error будет перехвачен.
 */
class CatchErrorTest {
    @Test
    void main() {
        try {
            myMethod();
        } catch (StackOverflowError s) {
            System.out.println(s);
        }
    }

    public void myMethod() {
        myMethod();
    }
}
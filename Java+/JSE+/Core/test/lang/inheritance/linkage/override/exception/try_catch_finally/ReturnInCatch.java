package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * Will a finally block execute even if the catch block defines a return statement?
 */
class ReturnInCatch {

    @Test
    void main() {
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("catch");
            return;
        } finally {
            System.out.println("finally");
        }
    }
}
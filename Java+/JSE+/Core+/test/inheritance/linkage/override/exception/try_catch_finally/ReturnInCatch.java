package inheritance.linkage.override.exception.try_catch_finally;

import org.junit.Test;

/**
 * Will a finally block execute even if the catch block defines a return statement?
 */
public class ReturnInCatch {

    @Test
    public void main() {
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
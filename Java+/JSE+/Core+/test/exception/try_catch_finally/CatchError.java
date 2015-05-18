package exception.try_catch_finally;

import org.junit.Test;

/**
 * Error будет перехвачен.
 */
public class CatchError {
    @Test
    public void main() {
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
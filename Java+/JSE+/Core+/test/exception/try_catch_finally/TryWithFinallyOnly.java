package exception.try_catch_finally;

import org.junit.Test;

/**
 * A try block may be followed by either a catch or a finally block or both. But
 * a finally block alone wouldn’t suffice if code in the try block throws a
 * checked exception. In this case, you need to catch the checked exception or
 * declare it to be thrown by your method. Otherwise your code won’t compile.
 */
public class TryWithFinallyOnly {
    @Test
    public void main() {
        try {
            runtime();
        } catch (RuntimeException e) {
            System.out.println(e.getClass().getSimpleName());
        }

        System.out.println();

        try {
            checked();
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
    }

    private void runtime() {
        try {
            throw new RuntimeException();
        } finally {
            System.out.println("runtime finally");
        }
    }

    private void checked() throws Exception {
        try {
            throw new Exception();
        } finally {
            System.out.println("checked finally");
        }
    }
}
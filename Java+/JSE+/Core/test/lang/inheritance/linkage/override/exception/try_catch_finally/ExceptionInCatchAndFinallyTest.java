package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * Какое исключение будет выброшено наружу,
 * если и в catch, и в finally выброшены исключения?
 */
class ExceptionInCatchAndFinallyTest {
    @Test
    void main() {
        try {
            method();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    private void method() {
        try {
            throw new IllegalArgumentException("try");
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("catch");
        } finally {
            throw new ArithmeticException("finally");
        }
    }
}
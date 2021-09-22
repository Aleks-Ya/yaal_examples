package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

/**
 * What happens if a finally block modifies the value returned from a catch block?
 * <p/>
 * If a catch block returns a primitive data type, a finally block can’t modify the
 * value being returned by it.
 * If a catch block returns an object, a finally block can modify the value being
 * returned by it.
 */
class FinallyModifyReturnedValue {

    @Test
    void main() {
        out.printf("object main: %s%n%n", object());
        out.printf("immutable main: %s%n%n", immutable());
        out.printf("primitive main: %s", primitive());
    }

    private StringBuilder object() {
        StringBuilder result = new StringBuilder("catch");
        try {
            out.println(new int[]{}[0]);
        } catch (Exception e) {
            out.println("catch: " + result);
            return result;
        } finally {
            result.append(" finally");
            out.println("finally: " + result);
        }
        return result;
    }

    private String immutable() {
        String result = "catch";
        try {
            out.println(new int[]{}[0]);
        } catch (Exception e) {
            out.println("catch: " + result);
            return result;
        } finally {
            result = "finally";
            out.println("finally: " + result);
        }
        return result;
    }

    private int primitive() {
        int result = 0;
        try {
            out.println(new int[]{}[0]);
        } catch (Exception e) {
            out.println("catch: " + result);
            return result;
        } finally {
            result = 10;
            out.println("finally: " + result);
        }
        return result;
    }
}
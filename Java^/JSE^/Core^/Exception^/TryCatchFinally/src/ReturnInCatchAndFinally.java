/**
 * What happens if both a catch and a finally block define return statements?
 *
 * If both catch and finally blocks define return statements, the calling method
 * will receive the value from the finally block.
 */
public class ReturnInCatchAndFinally {
    public static void main(String[] args) {
        System.out.println(method());
    }

    private static String method() {
        try {
            throw new Exception();
        } catch (Exception e) {
            return "catch";
        } finally {
            return "finally";
        }
    }
}
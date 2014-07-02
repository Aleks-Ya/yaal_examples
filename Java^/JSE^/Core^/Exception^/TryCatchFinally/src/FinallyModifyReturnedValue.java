/**
 * What happens if a finally block modifies the value returned from a catch block?
 */
public class FinallyModifyReturnedValue {
    public static void main(String[] args) {
        System.out.printf("main: %s%n%n", object());
        System.out.printf("main: %s", primitive());
    }

    private static String object() {
        String result = "catch";
        try {
            System.out.println(new int[]{}[0]);
        } catch (Exception e) {
            return result;
        } finally {
            result = "finally";
        }
        return result;
    }

    private static int primitive() {
        int result = 0;
        try {
            System.out.println(new int[]{}[0]);
        } catch (Exception e) {
            System.out.println("catch: " + result);
            return result;
        } finally {
            result += 10;
            System.out.println("finally: " + result);
        }
        return result;
    }
}
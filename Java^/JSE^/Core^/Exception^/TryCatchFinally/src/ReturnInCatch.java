/**
 * Will a finally block execute even if the catch block defines a return statement?
 */
public class ReturnInCatch {
    public static void main(String[] args) {
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
/**
 * Будет ли перехвачен Error?
 */
public class CatchError {
    public static void main(String args[]) {
        try {
            myMethod();
        } catch (StackOverflowError s) {
            System.out.println(s);
        }
    }

    public static void myMethod() {
        myMethod();
    }
}
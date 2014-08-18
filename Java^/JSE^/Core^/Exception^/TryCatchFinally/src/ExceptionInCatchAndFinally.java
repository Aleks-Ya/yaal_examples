/**
 * Какое исключение будет выброшено наружу, 
 * если и в catch, и в finally выброшены исключения?
 */
public class ExceptionInCatchAndFinally {
    public static void main(String[] args) {
        try {
            method();
        } catch(Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    private static void method() {
        try {
            throw new IllegalArgumentException("try");
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("catch");
        } finally {
            throw new ArithmeticException("finally");
        }
    }
}
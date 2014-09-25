import org.junit.Test;

public class Main {
    @Test
    public void main() {
        int a = 10;
        a = ++a + a + --a - --a + a++;
        System.out.println("a=" + a);

        int b = 10;
        b = b++ + b + b-- - b-- + ++b;
        System.out.println("b=" + b);
    }
}
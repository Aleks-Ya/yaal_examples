package lang.operator.unary_inc_dec;

import org.junit.jupiter.api.Test;

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
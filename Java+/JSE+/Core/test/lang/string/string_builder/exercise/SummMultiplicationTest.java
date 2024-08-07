package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

/**
 * Вычислить: (1+2)*(1+2+3)*...*(1+2+...+10).
 */
class SummMultiplicationTest {
    @Test
    void main() {
        long mult = 1;
        int sum = 1;
        for (int i = 2; i < 10; i++) {
            sum += i;
            out.println("Sum: " + sum);
            mult *= sum;
        }
        out.println("\nMultiplication: " + mult);
    }
}

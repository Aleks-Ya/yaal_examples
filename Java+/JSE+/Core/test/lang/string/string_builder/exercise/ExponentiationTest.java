package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

/**
 * Возвести число в степень.
 */ 
class ExponentiationTest {
    @Test
    void main() {
		final int num = 2;
		final int exponent = 4;
		long result = num;
		for(int i = 1; i < exponent; i++) {
			result *= num;
		}
		System.out.printf("%d^%d=%d%n", num, exponent, result);
    }
}

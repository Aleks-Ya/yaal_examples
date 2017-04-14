package lang.string.string_builder.exercise;

import org.junit.Test;

/**
 * Возвести число в степень.
 */ 
public class Exponentiation {
    @Test
    public void main() {
		final int num = 2;
		final int exponent = 4;
		long result = num;
		for(int i = 1; i < exponent; i++) {
			result *= num;
		}
		System.out.printf("%d^%d=%d%n", num, exponent, result);
    }
}

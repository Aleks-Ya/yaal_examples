import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

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

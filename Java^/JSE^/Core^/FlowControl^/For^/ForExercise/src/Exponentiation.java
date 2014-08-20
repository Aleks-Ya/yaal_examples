/**
 * Возвести число в степень.
 */ 
public class Exponentiation {
    public static void main(String[] args) {
		final int num;
		final int exponent;
		try {
			num = Integer.parseInt(args[0]);
			exponent = Integer.parseInt(args[1]);
		} catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
			throw new IllegalArgumentException("Число и степень не заданы");
		}
		
		long result = num;
		for(int i = 1; i < exponent; i++) {
			result *= num;
		}
		System.out.printf("%d^%d=%d%n", num, exponent, result);
    }
}
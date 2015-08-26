package intuit.combinatorics;

/**
 * Вычисляет факториал (n! = 1*2*3*...*n).
 */
public class Factorial {
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be positive.");
        }

        long f = 1;
        for (int i = 2; i <= n; i++) {
            f *= i;
        }
        return f;
    }
}
package intuit.combinatorics.set;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Определить количество простых чисел во множестве натуральных чисел от 1 до n
 * методом "решета Эратосфена".
 */
public class SieveTest {
    @Test
    public void empty() {
        assertThat(Sieve.findEasyNumbers(0), emptyCollectionOf(Integer.class));
        assertThat(Sieve.findEasyNumbers(1), emptyCollectionOf(Integer.class));
    }

    @Test
    public void two() {
        assertThat(Sieve.findEasyNumbers(2), contains(2));
        assertThat(Sieve.findEasyNumbers(3), contains(2, 3));
        assertThat(Sieve.findEasyNumbers(4), contains(2, 3));
        assertThat(Sieve.findEasyNumbers(6), contains(2, 3, 5));
        assertThat(Sieve.findEasyNumbers(7), contains(2, 3, 5, 7));
        assertThat(Sieve.findEasyNumbers(11), contains(2, 3, 5, 7, 11));
        assertThat(Sieve.findEasyNumbers(13), contains(2, 3, 5, 7, 11, 13));
        assertThat(Sieve.findEasyNumbers(17), contains(2, 3, 5, 7, 11, 13, 17));
        assertThat(Sieve.findEasyNumbers(19), contains(2, 3, 5, 7, 11, 13, 17, 19));
        assertThat(Sieve.findEasyNumbers(23), contains(2, 3, 5, 7, 11, 13, 17, 19, 23));
        assertThat(Sieve.findEasyNumbers(27), contains(2, 3, 5, 7, 11, 13, 17, 19, 23));
    }

    @Test
    public void hundred() {
        assertThat(Sieve.findEasyNumbers(100), contains(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97));
    }

    @Test
    public void stress() {
        long start = System.currentTimeMillis();
        assertThat(Sieve.findEasyNumbers(3571), hasSize(500));
        long finish = System.currentTimeMillis();
        System.out.println("Stress: " + (finish - start) + "ms");
    }
}

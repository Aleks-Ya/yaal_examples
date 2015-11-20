package exercises;

import org.junit.Test;
import reactor.rx.Streams;

import java.util.Arrays;
import java.util.Random;

/**
 * Дано вещественное число А.
 * Найти в последовательности чисел первое число больше А.
 */
public class FindNumber {
    @Test
    public void testName() {
        Random r = new Random();
        int count = Streams.generate(() -> r.nextInt(15) + 3).next().get();
        int A = Streams.generate(() -> r.nextInt(100)).next().get();
        System.out.println("A=" + A);
        System.out.println("Count=" + count);
        Integer[] numbers = new Integer[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = r.nextInt(100);
        }
        System.out.println("Array: " + Arrays.toString(numbers));

        Integer result = Streams.from(numbers)
                .observe(i -> System.out.print(i + " "))
                .filter(i -> i > A).next().get();

        System.out.println("\nResult=" + result);
    }
}

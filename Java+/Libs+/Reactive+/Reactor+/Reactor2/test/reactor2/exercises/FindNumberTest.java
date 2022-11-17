package reactor2.exercises;

import org.junit.jupiter.api.Test;
import reactor.rx.Streams;

import java.util.Arrays;
import java.util.Random;

/**
 * Дано вещественное число А.
 * Найти в последовательности чисел первое число больше А.
 */
class FindNumberTest {
    @Test
    void testName() {
        var r = new Random();
        int count = Streams.generate(() -> r.nextInt(15) + 3).next().get();
        int A = Streams.generate(() -> r.nextInt(100)).next().get();
        System.out.println("A=" + A);
        System.out.println("Count=" + count);
        var numbers = new Integer[count];
        for (var i = 0; i < count; i++) {
            numbers[i] = r.nextInt(100);
        }
        System.out.println("Array: " + Arrays.toString(numbers));

        var result = Streams.from(numbers)
                .observe(i -> System.out.print(i + " "))
                .filter(i -> i > A).next().get();

        System.out.println("\nResult=" + result);
    }
}

package intuit.combinatorics.split;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

/**
 * Задача: напечатать все варианты слов, полученных перестановкой букв в заданном слове, а также напечатать их количество.
 */
public class LetterCombinationsTest {
    @Test
    void test() {
        assertThat(LetterCombinations.combine("ABA"), containsInAnyOrder("ABA", "AAB", "BAA"));
        assertThat(LetterCombinations.combine("миссисипи"), hasSize(2520));
    }

    @Test
    void time() {
        long start = System.currentTimeMillis();
        Set<String> list = LetterCombinations.combine("абвгдеё");
        long finish = System.currentTimeMillis();
        System.out.printf("Time=%d ms, combinations=%d", finish - start, list.size());
    }
}

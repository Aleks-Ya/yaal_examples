package lombok;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Docs: https://projectlombok.org/features/val
 */
class ValTest {

    @Test
    void example() {
        val example = new ArrayList<String>();
        example.add("Hello, World!");
        assertThat(example).containsExactly("Hello, World!");
        val foo = example.get(0);
        assertThat(foo.toLowerCase()).isEqualTo("hello, world!");
    }

    @Test
    void example2() {
        val map = new HashMap<Integer, String>();
        map.put(0, "zero");
        map.put(5, "five");
        for (val entry : map.entrySet()) {
            System.out.printf("%d: %s\n", entry.getKey(), entry.getValue());
        }
    }
}

package jupiter.assertion;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CollectionAssert {

    @Test
    void iterableEquals() {
        Iterable<Integer> act = Arrays.asList(1, 2, 3);
        Iterable<Integer> exp = Arrays.asList(1, 2, 3);
        assertIterableEquals(exp, act);
    }

}

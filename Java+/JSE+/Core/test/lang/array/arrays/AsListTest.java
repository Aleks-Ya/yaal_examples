package lang.array.arrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AsListTest {
    @Test
    void longCollection() {
        List<Long> a = Arrays.asList(2L, 1L, 4L, 3L);
    }
}

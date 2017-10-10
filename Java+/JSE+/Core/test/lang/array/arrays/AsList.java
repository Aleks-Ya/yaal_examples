package lang.array.arrays;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Использование метода Arrays#asList.
 */
public class AsList {

    /**
     * Чтобы получить коллекцию Long нужно ВСЕ элементы дополнить суффиксом L.
     */
    @Test
    public void longCollection() throws Exception {
        List<Long> a = Arrays.asList(2L, 1L, 4L, 3L);
    }
}

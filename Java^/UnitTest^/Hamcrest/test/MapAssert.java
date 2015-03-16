import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

/**
 * Проверка карт.
 */
public class MapAssert {
    @Test
    public void testName() throws Exception {
        final Map<Integer, String> map = new HashMap<>();
        map.put(100, "Dollars");
        assertThat(map, hasEntry(100, "Dollars"));
        assertThat(map, hasKey(100));
        assertThat(map, hasValue("Dollars"));
    }
}
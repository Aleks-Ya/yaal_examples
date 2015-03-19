import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
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
        final Map<Integer, String> actual = new HashMap<>();
        actual.put(100, "Dollars");
        actual.put(200, "Rouble");

        final Map<Integer, String> expected = new HashMap<>();
        expected.put(200, "Rouble");
        expected.put(100, "Dollars");

        assertThat(actual, hasEntry(100, "Dollars"));
        assertThat(actual, hasKey(100));
        assertThat(actual, hasValue("Dollars"));
        assertThat(actual.entrySet(), equalTo(expected.entrySet()));
    }
}
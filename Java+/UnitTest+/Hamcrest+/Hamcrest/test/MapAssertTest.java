import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;

/**
 * Проверка карт.
 */
class MapAssertTest {
    @Test
    void testName() {
        var actual = new HashMap<Integer, String>();
        actual.put(100, "Dollars");
        actual.put(200, "Rouble");

        var expected = new HashMap<Integer, String>();
        expected.put(200, "Rouble");
        expected.put(100, "Dollars");

        assertThat(new HashMap<>(), anEmptyMap());
        assertThat(actual, aMapWithSize(2));
        assertThat(actual, hasEntry(100, "Dollars"));
        assertThat(actual, hasKey(100));
        assertThat(actual, hasValue("Dollars"));
        assertThat(actual.entrySet(), equalTo(expected.entrySet()));
    }
}
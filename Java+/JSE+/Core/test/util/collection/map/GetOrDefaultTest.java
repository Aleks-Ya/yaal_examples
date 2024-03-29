package util.collection.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using Map#getOrDefault.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
class GetOrDefaultTest {

    @Test
    void getOrDefault() {
        Map<String, Integer> map = new HashMap<>();
        var expValue = 100;
        var actValue = map.getOrDefault("a", expValue);
        assertThat(actValue).isEqualTo(expValue);
    }

    @Test
    void getOrDefaultCapture() {
        Map<String, ?> map = new HashMap<>();
        Integer expValue = 100;
//        Integer actValue = map.getOrDefault("a", expValue); // not compile
        var value = map.get("a");
        var actValue = value != null ? (Integer) value : expValue;
        assertThat(actValue).isEqualTo(expValue);
    }
}

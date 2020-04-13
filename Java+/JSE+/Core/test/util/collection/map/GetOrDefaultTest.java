package util.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using Map#getOrDefault.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class GetOrDefaultTest {

    @Test
    public void getOrDefault() {
        Map<String, Integer> map = new HashMap<>();
        int expValue = 100;
        Integer actValue = map.getOrDefault("a", expValue);
        assertThat(actValue, equalTo(expValue));
    }

    @Test
    public void getOrDefaultCapture() {
        Map<String, ?> map = new HashMap<>();
        Integer expValue = 100;
//        Integer actValue = map.getOrDefault("a", expValue); // not compile
        Object value = map.get("a");
        Integer actValue = value != null ? (Integer) value : expValue;
        assertThat(actValue, equalTo(expValue));
    }
}

package util.concurrent.collection;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentHashMapTest {

    /**
     * ConcurrentHashMap#computeIfAbsent puts computed value to the map.
     */
    @Test
    public void computeIfAbsentPutValueToMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        String key = "abc";
        Integer value = map.computeIfAbsent(key, s -> 1);
        assertThat(value, equalTo(1));
        assertThat(map.get(key), equalTo(1));
    }
}
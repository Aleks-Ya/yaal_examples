package util.concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentHashMapTest {

    /**
     * ConcurrentHashMap#computeIfAbsent puts computed value to the map.
     */
    @Test
    void computeIfAbsentPutValueToMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        String key = "abc";
        Integer value = map.computeIfAbsent(key, s -> 1);
        assertThat(value).isEqualTo(1);
        assertThat(map.get(key)).isEqualTo(1);
    }

    /**
     * Delete element of a ConcurrentHashMap during iterating.
     */
    @Test
    void removeByIterator() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("abc", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertThat(map).isEmpty();
    }
}
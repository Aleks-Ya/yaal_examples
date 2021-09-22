package util.concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;

class ConcurrentHashMapTest {

    /**
     * ConcurrentHashMap#computeIfAbsent puts computed value to the map.
     */
    @Test
    void computeIfAbsentPutValueToMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        String key = "abc";
        Integer value = map.computeIfAbsent(key, s -> 1);
        assertThat(value, equalTo(1));
        assertThat(map.get(key), equalTo(1));
    }

    /**
     * Delete element of a ConcurrentHashMap during iterating.
     */
    @Test
    void removeByIterator() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("abc", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            it.next();
            it.remove();
        }
        assertThat(map, anEmptyMap());
    }
}
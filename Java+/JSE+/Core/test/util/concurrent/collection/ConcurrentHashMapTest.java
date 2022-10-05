package util.concurrent.collection;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentHashMapTest {

    /**
     * ConcurrentHashMap#computeIfAbsent puts computed value to the map.
     */
    @Test
    void computeIfAbsentPutValueToMap() {
        var map = new ConcurrentHashMap<String, Integer>();
        var key = "abc";
        var value = map.computeIfAbsent(key, s -> 1);
        assertThat(value).isEqualTo(1);
        assertThat(map).containsEntry(key, 1);
    }

    /**
     * Delete element of a ConcurrentHashMap during iterating.
     */
    @Test
    void removeByIterator() {
        var map = new ConcurrentHashMap<String, Integer>();
        map.put("abc", 1);
        var it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertThat(map).isEmpty();
    }
}
package collection

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

class MapTest {

    @Test
    void test() {
        def map = [name: "Gromit", likes: "cheese", id: 1234]
        assert map.get("name") == "Gromit"
        assert map.get("id") == 1234
        assert map["name"] == "Gromit"
        assert map['id'] == 1234
        assert map instanceof Map

        def emptyMap = [:]
        assert emptyMap.size() == 0
        emptyMap.put("foo", 5)
        assert emptyMap.size() == 1
        assert emptyMap.get("foo") == 5
    }

    @Test
    void containsKey() {
        def map = ["John": 30, "Mary": 25]
        assertTrue(map.containsKey("Mary"))
        assertFalse(map.containsKey("Rick"))
    }
}
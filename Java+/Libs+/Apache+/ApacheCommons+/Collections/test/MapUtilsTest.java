import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;

class MapUtilsTest {

    @Test
    void isNotEmpty() {
        assertFalse(MapUtils.isNotEmpty(new HashMap<>()));
    }
}
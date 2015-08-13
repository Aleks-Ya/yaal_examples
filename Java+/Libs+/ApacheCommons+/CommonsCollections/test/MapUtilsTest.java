import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;

public class MapUtilsTest {

    @Test
    public void isNotEmpty() {
        assertFalse(MapUtils.isNotEmpty(new HashMap<>()));
    }
}
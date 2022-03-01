import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MapUtilsTest {

    @Test
    void isNotEmpty() {
        var isNotEmpty = MapUtils.isNotEmpty(new HashMap<>());
        assertThat(isNotEmpty).isFalse();
    }
}
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CollectionUtilsTest {

    @Test
    void isNotEmpty() {
        assertFalse(CollectionUtils.isNotEmpty(new ArrayList<>(0)));
    }
}
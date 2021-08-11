import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ArrayUtilsTest {
    @Test
    void main() {
        var arr = new int[]{100, 200};
        assertFalse(ArrayUtils.isEmpty(arr));
    }
}
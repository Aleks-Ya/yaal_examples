package base.using_avoiding_null;

import com.google.common.base.MoreObjects;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoreObjectsTest {
    @Test
    void firstNotNull() {
        Object o1 = null;
        var o2 = new Object();
        var firstNonNull = MoreObjects.firstNonNull(o1, o2);
        assertThat(firstNonNull).isEqualTo(o2);
    }
}
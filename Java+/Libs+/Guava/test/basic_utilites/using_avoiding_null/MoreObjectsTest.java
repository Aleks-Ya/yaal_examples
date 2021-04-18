package basic_utilites.using_avoiding_null;

import com.google.common.base.MoreObjects;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class MoreObjectsTest {
    @Test
    public void firstNotNull() {
        Object o1 = null;
        Object o2 = new Object();
        assertEquals(o2, MoreObjects.firstNonNull(o1, o2));
    }
}
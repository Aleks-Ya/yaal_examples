package basic_utilites.using_avoiding_null;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalTest {
    @Test
    public void use() throws Exception {
        Optional<Integer> possible = Optional.of(5);
        assertTrue(possible.isPresent());
        assertEquals(Integer.valueOf(5), possible.get());
    }

    @Test
    public void useNull() throws Exception {
        Optional<Integer> possible = Optional.fromNullable(null);
        assertFalse(possible.isPresent());
        //assertNull(possible.get());// IllegalArgumentException
    }
}

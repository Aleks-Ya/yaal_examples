package basic_utilites.using_avoiding_null;

import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

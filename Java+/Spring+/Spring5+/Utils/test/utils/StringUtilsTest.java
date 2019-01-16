package utils;

import org.junit.Test;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    public void isEmpty() {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertFalse(StringUtils.isEmpty("a"));
    }
}

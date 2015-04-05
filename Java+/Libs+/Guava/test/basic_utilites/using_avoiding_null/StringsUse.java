package basic_utilites.using_avoiding_null;

import com.google.common.base.Strings;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Test
public class StringsUse {

    @Test
    public void emptyToNull() {
        assertNull(Strings.emptyToNull(""));
        assertNotNull(Strings.emptyToNull("aa"));
    }

    @Test
    public void isNullOrEmpty() {
        assertTrue(Strings.isNullOrEmpty(null));
        assertTrue(Strings.isNullOrEmpty(""));
        assertFalse(Strings.isNullOrEmpty("aa"));
    }

    @Test
    public void nullToEmpty() {
        assertEquals("", Strings.nullToEmpty(null));
        assertEquals("", Strings.nullToEmpty(""));
        assertEquals("aa", Strings.nullToEmpty("aa"));
    }
}
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsUse {

    /**
     * Сравнение строк (безопасно с null).
     */
    @Test
    public void equalsIgnoreCase() {
        assertFalse(StringUtils.equalsIgnoreCase(null, ""));
    }

    /**
     * Пустая строка.
     */
    @Test
    public void isBlank() {
        assertTrue(StringUtils.isBlank(null));
    }
}

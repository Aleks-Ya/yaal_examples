import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    /**
     * Сравнение строк (безопасно с null).
     */
    @Test
    void equalsIgnoreCase() {
        assertFalse(StringUtils.equalsIgnoreCase(null, ""));
    }

    /**
     * Пустая строка.
     */
    @Test
    void isBlank() {
        assertTrue(StringUtils.isBlank(null));
    }
}

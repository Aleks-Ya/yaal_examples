import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

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

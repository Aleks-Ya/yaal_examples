import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    /**
     * Сравнение строк (безопасно с null).
     */
    @Test
    void equalsIgnoreCase() {
        assertThat(StringUtils.equalsIgnoreCase(null, "")).isFalse();
    }

    /**
     * Пустая строка.
     */
    @Test
    void isBlank() {
        assertThat(StringUtils.isBlank(null)).isTrue();
    }
}

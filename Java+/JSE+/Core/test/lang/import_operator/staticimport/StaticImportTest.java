package lang.import_operator.staticimport;

import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;
import static lang.import_operator.staticimport.other.OtherPackage.number;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Статический импорт.
 */
class StaticImportTest {
    @Test
    void normal() {
        assertThat(1 < MAX_VALUE).isTrue();
        assertThat(format("%s", "abc")).isEqualTo("abc");
    }

    /**
     * Конфликт имен поля и метода нет: метод дополняется скобками.
     */
    @Test
    void conflict() {
        assertThat(number()).isEqualTo(2);
        assertThat(number).isEqualTo(1);
    }
}

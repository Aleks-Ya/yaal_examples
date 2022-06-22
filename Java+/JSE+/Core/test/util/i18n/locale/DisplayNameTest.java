package util.i18n.locale;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Варианты использования метода Locale#getDisplayName();
 */
class DisplayNameTest {

    /**
     * Возвращает название в локали по-умолчанию.
     */
    @Test
    void defaultLocale() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertThat(norwegianNorwayBokmel.getDisplayName()).isEqualTo("Norwegian (Norway, Bokmål)");

        var rus = new Locale("ru", "RU");
        assertThat(norwegianNorwayBokmel.getDisplayName(rus)).isEqualTo("норвежский (Норвегия, Bokmål)");
    }

    /**
     * Возвращает название в заданной локали.
     */
    @Test
    void specifiedLocale() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");

        var displayLocal = new Locale("ru", "RU");
        var displayName = norwegianNorwayBokmel.getDisplayName(displayLocal);

        assertThat(displayName).isEqualTo("норвежский (Норвегия, Bokmål)");
    }
}
package util.i18n.locale;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Варианты использования метода Locale#getDisplayName();
 */
class DisplayName {

    /**
     * Возвращает название в локали по-умолчанию.
     */
    @Test
    void defaultLocale() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertEquals("Norwegian (Norway,Bokmål)", norwegianNorwayBokmel.getDisplayName());

        var rus = new Locale("ru", "RU");
        assertEquals("норвежский (Норвегия,Bokmål)", norwegianNorwayBokmel.getDisplayName(rus));
    }

    /**
     * Возвращает название в заданной локали.
     */
    @Test
    void specifiedLocale() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");

        var displayLocal = new Locale("ru", "RU");
        var displayName = norwegianNorwayBokmel.getDisplayName(displayLocal);

        assertEquals("норвежский (Норвегия,Bokmål)", displayName);
    }
}
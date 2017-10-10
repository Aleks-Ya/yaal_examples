package util.i18n.locale;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Варианты использования метода Locale#getDisplayName();
 */
public class DisplayName {

    /**
     * Возвращает название в локали по-умолчанию.
     */
    @Test
    public void defaultLocale() {
        Locale norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertEquals("Norwegian (Norway,Bokmål)", norwegianNorwayBokmel.getDisplayName());

        Locale rus = new Locale("ru", "RU");
        assertEquals("норвежский (Норвегия,Bokmål)", norwegianNorwayBokmel.getDisplayName(rus));
    }

    /**
     * Возвращает название в заданной локали.
     */
    @Test
    public void specifiedLocale() {
        Locale norwegianNorwayBokmel = new Locale("no", "NO", "B");

        Locale displayLocal = new Locale("ru", "RU");
        String displayName = norwegianNorwayBokmel.getDisplayName(displayLocal);

        assertEquals("норвежский (Норвегия,Bokmål)", displayName);
    }
}
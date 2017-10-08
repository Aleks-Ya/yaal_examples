package core.util.i18n.locale;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Способы получения объекта Local.
 */
public class GetLocal {

    /**
     * По-умолчанию.
     */
    @Test
    public void defaultLocal() {
        Locale def = Locale.getDefault();
        assertEquals("en", def.getLanguage());
        assertEquals("US", def.getCountry());
        assertEquals("", def.getVariant());
    }

    /**
     * По-умолчанию.
     */
    @Test
    public void available() {
        Locale[] locales = Locale.getAvailableLocales();
        assertTrue(locales.length > 100);
    }

    /**
     * Язык
     */
    @Test
    public void language() {
        Locale german = new Locale("de");
        assertEquals("de", german.getLanguage());
        assertEquals("", german.getCountry());
        assertEquals("", german.getVariant());
    }

    /**
     * Язык + Географическое расположение
     */
    @Test
    public void languageAndCountry() {
        Locale german = new Locale("de", "DE");
        assertEquals("de", german.getLanguage());
        assertEquals("DE", german.getCountry());
        assertEquals("", german.getVariant());
    }

    /**
     * Язык + Географическое расположение + Вариант
     */
    @Test
    public void languageAndCountryAndVariant() {
        Locale norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertEquals("no", norwegianNorwayBokmel.getLanguage());
        assertEquals("NO", norwegianNorwayBokmel.getCountry());
        assertEquals("B", norwegianNorwayBokmel.getVariant());
    }
}
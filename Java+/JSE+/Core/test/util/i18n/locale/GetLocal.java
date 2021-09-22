package util.i18n.locale;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Способы получения объекта Local.
 */
class GetLocal {

    /**
     * По-умолчанию.
     */
    @Test
    void defaultLocal() {
        var def = Locale.getDefault();
        assertEquals("en", def.getLanguage());
        assertEquals("US", def.getCountry());
        assertEquals("", def.getVariant());
    }

    /**
     * По-умолчанию.
     */
    @Test
    void available() {
        var locales = Locale.getAvailableLocales();
        assertTrue(locales.length > 100);
    }

    /**
     * Язык
     */
    @Test
    void language() {
        var german = new Locale("de");
        assertEquals("de", german.getLanguage());
        assertEquals("", german.getCountry());
        assertEquals("", german.getVariant());
    }

    /**
     * Язык + Географическое расположение
     */
    @Test
    void languageAndCountry() {
        var german = new Locale("de", "DE");
        assertEquals("de", german.getLanguage());
        assertEquals("DE", german.getCountry());
        assertEquals("", german.getVariant());
    }

    /**
     * Язык + Географическое расположение + Вариант
     */
    @Test
    void languageAndCountryAndVariant() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertEquals("no", norwegianNorwayBokmel.getLanguage());
        assertEquals("NO", norwegianNorwayBokmel.getCountry());
        assertEquals("B", norwegianNorwayBokmel.getVariant());
    }
}
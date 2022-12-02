package util.i18n.locale;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Способы получения объекта Local.
 */
class GetLocalTest {

    /**
     * По-умолчанию.
     */
    @Test
    void defaultLocal() {
        var def = Locale.getDefault();
        assertThat(def.getLanguage()).isEqualTo("en");
        assertThat(def.getCountry()).isEqualTo("US");
        assertThat(def.getVariant()).isEmpty();
    }

    /**
     * По-умолчанию.
     */
    @Test
    void available() {
        var locales = Locale.getAvailableLocales();
        assertThat(locales.length > 100).isTrue();
    }

    /**
     * Язык
     */
    @Test
    void language() {
        var german = new Locale("de");
        assertThat(german.getLanguage()).isEqualTo("de");
        assertThat(german.getCountry()).isEmpty();
        assertThat(german.getVariant()).isEmpty();
    }

    /**
     * Язык + Географическое расположение
     */
    @Test
    void languageAndCountry() {
        var german = new Locale("de", "DE");
        assertThat(german.getLanguage()).isEqualTo("de");
        assertThat(german.getCountry()).isEqualTo("DE");
        assertThat(german.getVariant()).isEmpty();
    }

    /**
     * Язык + Географическое расположение + Вариант
     */
    @Test
    void languageAndCountryAndVariant() {
        var norwegianNorwayBokmel = new Locale("no", "NO", "B");
        assertThat(norwegianNorwayBokmel.getLanguage()).isEqualTo("no");
        assertThat(norwegianNorwayBokmel.getCountry()).isEqualTo("NO");
        assertThat(norwegianNorwayBokmel.getVariant()).isEqualTo("B");
    }
}
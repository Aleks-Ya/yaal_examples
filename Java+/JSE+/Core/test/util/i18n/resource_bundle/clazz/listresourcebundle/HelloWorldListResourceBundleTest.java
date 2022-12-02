package util.i18n.resource_bundle.clazz.listresourcebundle;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Hello World на пакетах ресурсов в виде java-классов.
 * Использован ListResourceBundle для реализации пакета ресурсов.
 */
class HelloWorldListResourceBundleTest {
    static final String LIGHT_KEY = "light";
    static final String DARK_KEY = "dark";
    private static final String BASE_NAME = "util.i18n.resource_bundle.clazz.listresourcebundle.Colors";

    @Test
    void ru() {
        var locale = new Locale("ru", "RU");
        var rb = ResourceBundle.getBundle(BASE_NAME, locale);
        assertThat(rb.getObject(LIGHT_KEY)).isEqualTo(Color.WHITE);
        assertThat(rb.getObject(DARK_KEY)).isEqualTo(Color.BLACK);
    }

    @Test
    void en() {
        var locale = new Locale("en", "EN");
        var rb = ResourceBundle.getBundle(BASE_NAME, locale);
        assertThat(rb.getObject(LIGHT_KEY)).isEqualTo(Color.YELLOW);
        assertThat(rb.getObject(DARK_KEY)).isEqualTo(Color.GRAY);
    }
}
package util.i18n.resource_bundle.clazz.listresourcebundle;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Hello World на пакетах ресурсов в виде java-классов.
 * Использован ListResourceBundle для реализации пакета ресурсов.
 */
class HelloWorldListResourceBundleTest {
    static final String LIGHT_KEY = "light";
    static final String DARK_KEY = "dark";

    @Test
    void ru() {
        var rb = ResourceBundle.getBundle("util.i18n.resource_bundle.clazz.listresourcebundle.Colors", new Locale("ru", "RU"));
        assertEquals(Color.WHITE, rb.getObject(LIGHT_KEY));
        assertEquals(Color.BLACK, rb.getObject(DARK_KEY));
    }

    @Test
    void en() {
        var rb = ResourceBundle.getBundle("util.i18n.resource_bundle.clazz.listresourcebundle.Colors", new Locale("en", "EN"));
        assertEquals(Color.YELLOW, rb.getObject(LIGHT_KEY));
        assertEquals(Color.GRAY, rb.getObject(DARK_KEY));
    }
}
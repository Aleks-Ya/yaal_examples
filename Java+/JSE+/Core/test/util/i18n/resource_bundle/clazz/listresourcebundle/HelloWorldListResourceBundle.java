package util.i18n.resource_bundle.clazz.listresourcebundle;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

/**
 * Hello World на пакетах ресурсов в виде java-классов.
 * Использован ListResourceBundle для реализации пакета ресурсов.
 */
public class HelloWorldListResourceBundle {
    static final String LIGHT_KEY = "light";
    static final String DARK_KEY = "dark";

    @Test
    public void ru() {
        ResourceBundle rb = ResourceBundle.getBundle("util.i18n.resource_bundle.clazz.listresourcebundle.Colors", new Locale("ru", "RU"));
        assertEquals(Color.WHITE, rb.getObject(LIGHT_KEY));
        assertEquals(Color.BLACK, rb.getObject(DARK_KEY));
    }

    @Test
    public void en() {
        ResourceBundle rb = ResourceBundle.getBundle("util.i18n.resource_bundle.clazz.listresourcebundle.Colors", new Locale("en", "EN"));
        assertEquals(Color.YELLOW, rb.getObject(LIGHT_KEY));
        assertEquals(Color.GRAY, rb.getObject(DARK_KEY));
    }
}
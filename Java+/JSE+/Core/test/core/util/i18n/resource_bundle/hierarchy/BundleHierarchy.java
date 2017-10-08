package core.util.i18n.resource_bundle.hierarchy;

import org.junit.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

/**
 * Поиск локализованной строки по иерархии ресурсов.
 */
public class BundleHierarchy {

    /**
     * Ищем строку в родительских пакетах.
     */
    @Test
    public void fromChild() {
        ResourceBundle parent = ResourceBundle.getBundle(
                "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU", "vologda"));
        assertEquals("ru RU vologda", parent.getString("ruRUvologdaKey"));
        assertEquals("ru RU", parent.getString("ruRUKey"));
        assertEquals("ru", parent.getString("ruKey"));
        assertEquals("nothing", parent.getString("Key"));
    }

    /**
     * Попытка искать строку, находяюущюся в потомке.
     */
    @Test(expected = MissingResourceException.class)
    public void notFound() {
        ResourceBundle parent = ResourceBundle.getBundle(
                "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU"));
        assertEquals("ru RU", parent.getString("ruRUvologdaKey"));
    }
}
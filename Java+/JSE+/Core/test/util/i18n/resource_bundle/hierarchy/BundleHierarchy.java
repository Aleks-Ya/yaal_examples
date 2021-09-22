package util.i18n.resource_bundle.hierarchy;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Поиск локализованной строки по иерархии ресурсов.
 */
class BundleHierarchy {

    /**
     * Ищем строку в родительских пакетах.
     */
    @Test
    void fromChild() {
        var parent = ResourceBundle.getBundle(
                "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU", "vologda"));
        assertEquals("ru RU vologda", parent.getString("ruRUvologdaKey"));
        assertEquals("ru RU", parent.getString("ruRUKey"));
        assertEquals("ru", parent.getString("ruKey"));
        assertEquals("nothing", parent.getString("Key"));
    }

    /**
     * Попытка искать строку, находяюущюся в потомке.
     */
    @Test
    void notFound() {
        assertThrows(MissingResourceException.class, () -> {
            var parent = ResourceBundle.getBundle(
                    "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU"));
            parent.getString("ruRUvologdaKey");
        });
    }
}
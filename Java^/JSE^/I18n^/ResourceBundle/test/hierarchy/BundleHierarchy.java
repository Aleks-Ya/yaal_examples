package hierarchy;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

/**
 * Поиск локализованной строки по иерархии ресурсов.
 */
public class BundleHierarchy {
    @Test
    public void test() {
        ResourceBundle parent = ResourceBundle.getBundle("hierarchy.Names", new Locale("ru", "RU", "vologda"));
        assertEquals("ru RU vologda", parent.getString("ruRUvologdaKey"));
        assertEquals("ru RU", parent.getString("ruRUKey"));
        assertEquals("ru", parent.getString("ruKey"));
        assertEquals("nothing", parent.getString("Key"));
    }
}
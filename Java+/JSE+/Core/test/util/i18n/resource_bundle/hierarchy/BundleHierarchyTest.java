package util.i18n.resource_bundle.hierarchy;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Поиск локализованной строки по иерархии ресурсов.
 */
class BundleHierarchyTest {

    /**
     * Ищем строку в родительских пакетах.
     */
    @Test
    void fromChild() {
        var parent = ResourceBundle.getBundle(
                "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU", "vologda"));
        assertThat(parent.getString("ruRUvologdaKey")).isEqualTo("ru RU vologda");
        assertThat(parent.getString("ruRUKey")).isEqualTo("ru RU");
        assertThat(parent.getString("ruKey")).isEqualTo("ru");
        assertThat(parent.getString("Key")).isEqualTo("nothing");
    }

    /**
     * Попытка искать строку, находяюущюся в потомке.
     */
    @Test
    void notFound() {
        assertThatThrownBy(() -> {
            var parent = ResourceBundle.getBundle(
                    "util.i18n.resource_bundle.hierarchy.Names", new Locale("ru", "RU"));
            parent.getString("ruRUvologdaKey");
        }).isInstanceOf(MissingResourceException.class);
    }
}
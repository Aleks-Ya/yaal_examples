package lang.generics.methods;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * Неоднородный контейнер.
 * Пример из книги Дж. Блоха "Java. Эффективное програмирование" (статья 29).
 */
public class MultiTypeContainer {

    @Test
    public void testName() {
        Integer integer = 1;
        String string = "lang/string";

        Favorites c = new Favorites();
        c.putFavorite(Integer.class, integer);
        c.putFavorite(String.class, string);

        assertEquals(integer, c.getFavorite(Integer.class));
        assertEquals(string, c.getFavorite(String.class));
    }

    static class Favorites {
        private final Map<Class<?>, Object> favorites = new HashMap<>();

        public <T> void putFavorite(Class<T> type, T instance) {
            assert (type != null);
            favorites.put(type, instance);
        }

        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }
}

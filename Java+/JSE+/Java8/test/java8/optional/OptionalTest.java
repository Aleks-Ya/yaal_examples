package java8.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Использование класса-обертки Optional для замены null-значений.
 */
public class OptionalTest {

    /**
     * Использование Optional#of.
     */
    @Test
    public void of() {
        String expTitle = "Gravity";
        Optional<String> titleOptional = Optional.of(expTitle);
        Book expBook = new Book(titleOptional);
        Optional<Book> bookOptional = Optional.of(expBook);

        assertTrue(bookOptional.isPresent());
        assertSame(expBook, bookOptional.get());
        assertSame(expTitle, bookOptional.get().getTitle().get());
    }

    /**
     * Optional.of(null) бросает NPE.
     */
    @Test
    public void ofNPE() {
        assertThrows(NullPointerException.class, () -> Optional.of(null));
    }

    /**
     * Использование Optional#empty.
     */
    @Test
    public void empty() {
        Optional<Book> bookOptional = Optional.empty();
        assertFalse(bookOptional.isPresent());
    }

    /**
     * Использование Optional#ofNullable.
     */
    @Test
    public void ofNullable() {
        Optional<Book> bookOptional = Optional.ofNullable(null);
        assertFalse(bookOptional.isPresent());
    }

    /**
     * Избегание проверки на NPE с помощью маппинга Optional#map.
     */
    @Test
    public void map() {
        String s = null;
        Optional<Integer> opt = Optional.ofNullable(s).map(String::length);
        assertFalse(opt.isPresent());
    }

    /**
     * Замена null значением по-умолчанию с помощью Optional#orElse.
     */
    @Test
    public void orElse() {
        Optional<String> string = Optional.ofNullable(null);
        assertEquals("default", string.orElse("default"));
    }

    /**
     * Бросать исключение в случае null с помощью Optional#orElseThrow.
     */
    @Test
    public void orElseThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            Optional<String> string = Optional.ofNullable(null);
            string.orElseThrow(IllegalArgumentException::new);
        });
    }

    private class Book {
        private Optional<String> title;

        public Book(Optional<String> title) {
            this.title = title;
        }


        public Optional<String> getTitle() {
            return title;
        }
    }
}

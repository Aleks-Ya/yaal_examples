package java8.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Использование класса-обертки Optional для замены null-значений.
 */
class OptionalTest {

    /**
     * Использование Optional#of.
     */
    @Test
    void of() {
        String expTitle = "Gravity";
        Optional<String> titleOptional = Optional.of(expTitle);
        Book expBook = new Book(titleOptional);
        Optional<Book> bookOptional = Optional.of(expBook);

        assertThat(bookOptional).isPresent();
        assertThat(bookOptional.get()).isSameAs(expBook);
        assertThat(bookOptional.get().getTitle().get()).isSameAs(expTitle);
    }

    /**
     * Optional.of(null) бросает NPE.
     */
    @Test
    void ofNPE() {
        assertThatThrownBy(() -> Optional.of(null)).isInstanceOf(NullPointerException.class);
    }

    /**
     * Использование Optional#empty.
     */
    @Test
    void empty() {
        Optional<Book> bookOptional = Optional.empty();
        assertThat(bookOptional).isNotPresent();
    }

    /**
     * Использование Optional#ofNullable.
     */
    @Test
    void ofNullable() {
        Optional<Book> bookOptional = Optional.ofNullable(null);
        assertThat(bookOptional).isNotPresent();
    }

    /**
     * Избегание проверки на NPE с помощью маппинга Optional#map.
     */
    @Test
    void map() {
        String s = null;
        Optional<Integer> opt = Optional.ofNullable(s).map(String::length);
        assertThat(opt).isNotPresent();
    }

    /**
     * Замена null значением по-умолчанию с помощью Optional#orElse.
     */
    @Test
    void orElse() {
        Optional<String> string = Optional.ofNullable(null);
        assertThat(string.orElse("default")).isEqualTo("default");
    }

    /**
     * Бросать исключение в случае null с помощью Optional#orElseThrow.
     */
    @Test
    void orElseThrow() {
        assertThatThrownBy(() -> {
            Optional<String> string = Optional.ofNullable(null);
            string.orElseThrow(IllegalArgumentException::new);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static class Book {
        private Optional<String> title;

        public Book(Optional<String> title) {
            this.title = title;
        }


        public Optional<String> getTitle() {
            return title;
        }
    }
}

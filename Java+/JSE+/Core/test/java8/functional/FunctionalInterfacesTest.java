package java8.functional;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование функциональных интерфейсов.
 */
class FunctionalInterfacesTest {
    @Test
    void consumer() {
        var log = new StringBuilder();
        Consumer<String> logger = log::append;
        logger.accept("a");
        assertThat(log.toString()).isEqualTo("a");
    }

    @Test
    void function() {
        Function<String, Integer> length = String::length;
        assertThat(length.apply("a")).isEqualTo(1);
    }
}

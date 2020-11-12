package java8.functional;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Использование функциональных интерфейсов.
 */
public class FunctionalInterfaces {
    @Test
    public void consumer() {
        StringBuilder log = new StringBuilder();
        Consumer<String> logger = log::append;
        logger.accept("a");
        assertThat(log.toString(), equalTo("a"));
    }

    @Test
    public void function() {
        Function<String, Integer> length = String::length;
        assertThat(length.apply("a"), equalTo(1));
    }
}

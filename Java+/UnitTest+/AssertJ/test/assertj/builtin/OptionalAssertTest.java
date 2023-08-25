package assertj.builtin;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalAssertTest {
    @Test
    void empty() {
        Optional<String> optEmpty = Optional.empty();
        assertThat(optEmpty).isEmpty();
        assertThat(optEmpty).isNotPresent();
    }

    @Test
    void notEmpty() {
        var optNotEmpty = Optional.of("a");
        assertThat(optNotEmpty).isNotEmpty();
        assertThat(optNotEmpty).isPresent();
    }

    @Test
    void contains() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).contains(value);
    }

    @Test
    void hasValue() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).hasValue(value);
    }

    @Test
    void containsInstanceOf() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).containsInstanceOf(String.class);
    }

    @Test
    void map() {
        var value = "ab";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).map(String::length).isEqualTo(Optional.of(2));
    }

    @Test
    void hasValueSatisfying() {
        var value = "ab";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).hasValueSatisfying(s -> assertThat(s).hasSize(value.length()));
    }

}

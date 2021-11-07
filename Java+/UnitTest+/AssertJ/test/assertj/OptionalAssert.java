package assertj;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalAssert {
    @Test
    void optionalEmpty() {
        Optional<String> optEmpty = Optional.empty();
        assertThat(optEmpty).isEmpty();
        assertThat(optEmpty).isNotPresent();
    }

    @Test
    void optionalNotEmpty() {
        var optNotEmpty = Optional.of("a");
        assertThat(optNotEmpty).isNotEmpty();
        assertThat(optNotEmpty).isPresent();
    }

    @Test
    void optionalContains() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).contains(value);
    }

    @Test
    void optionalHasValue() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).hasValue(value);
    }

    @Test
    void optionalContainsInstanceOf() {
        var value = "a";
        var optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).containsInstanceOf(String.class);
    }

}

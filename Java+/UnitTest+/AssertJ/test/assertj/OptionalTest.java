package assertj;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTest {
    @Test
    public void optionalEmpty() {
        Optional<String> optEmpty = Optional.empty();
        assertThat(optEmpty).isEmpty();
        assertThat(optEmpty).isNotPresent();
    }

    @Test
    public void optionalNotEmpty() {
        Optional<String> optNotEmpty = Optional.of("a");
        assertThat(optNotEmpty).isNotEmpty();
        assertThat(optNotEmpty).isPresent();
    }

    @Test
    public void optionalContains() {
        String value = "a";
        Optional<String> optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).contains(value);
    }

    @Test
    public void optionalHasValue() {
        String value = "a";
        Optional<String> optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).hasValue(value);
    }

    @Test
    public void optionalContainsInstanceOf() {
        String value = "a";
        Optional<String> optNotEmpty = Optional.of(value);
        assertThat(optNotEmpty).containsInstanceOf(String.class);
    }

}

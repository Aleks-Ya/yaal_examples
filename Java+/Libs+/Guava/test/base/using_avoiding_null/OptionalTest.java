package base.using_avoiding_null;

import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalTest {
    @Test
    void use() {
        var possible = Optional.of(5);
        assertThat(possible.isPresent()).isTrue();
        assertThat(possible.get()).isEqualTo(Integer.valueOf(5));
    }

    @Test
    void useNull() {
        Optional<Integer> possible = Optional.fromNullable(null);
        assertThat(possible.isPresent()).isFalse();
    }
}

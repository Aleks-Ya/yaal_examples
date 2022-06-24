package java11.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalTest {
    private boolean present = false;
    private boolean orElse = false;

    @Test
    void or() {
        var opt = Optional.<Integer>empty().or(() -> Optional.of(1)).get();
        assertThat(opt).isEqualTo(1);
    }

    @Test
    void ifPresentOrElse() {
        Optional.empty().ifPresentOrElse(x -> present = true, () -> orElse = true);
        assertThat(present).isFalse();
        assertThat(orElse).isTrue();
    }
}

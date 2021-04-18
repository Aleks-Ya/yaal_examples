package java11.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalTest {
    private boolean present = false;
    private boolean orElse = false;

    @Test
    public void or() {
        Integer opt = Optional.<Integer>empty().or(() -> Optional.of(1)).get();
        assertThat(opt, equalTo(1));
    }

    @Test
    public void ifPresentOrElse() {
        Optional.empty().ifPresentOrElse(x -> present = true, () -> orElse = true);
        assertFalse(present);
        assertTrue(orElse);
    }
}

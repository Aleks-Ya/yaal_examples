package optional;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

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

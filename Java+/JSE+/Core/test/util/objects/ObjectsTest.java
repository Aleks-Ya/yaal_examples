package util.objects;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectsTest {
    @Test
    void requireNonNullElse() {
        Object o1 = null;
        Object o2 = new Object();
        var nonNull = Objects.requireNonNullElse(o1, o2);
        assertThat(nonNull).isEqualTo(o2);
    }
}
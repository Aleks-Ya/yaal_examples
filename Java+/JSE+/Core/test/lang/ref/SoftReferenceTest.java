package lang.ref;

import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;

import static org.assertj.core.api.Assertions.assertThat;

class SoftReferenceTest {
    @Test
    void createAndClear() {
        var referent = "abc";

        var reference = new SoftReference<>(referent);
        assertThat(reference.get()).isSameAs(referent);
        assertThat(reference.refersTo(referent)).isTrue();

        reference.clear();
        assertThat(reference.get()).isNull();
        assertThat(reference.refersTo(referent)).isFalse();
    }
}

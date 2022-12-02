package lang.ref;

import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;

import static org.assertj.core.api.Assertions.assertThat;

class WeakReferenceTest {
    @Test
    void createAndClear() {
        var referent = "abc";

        var reference = new WeakReference<>(referent);
        assertThat(reference.get()).isSameAs(referent);
        assertThat(reference.refersTo(referent)).isTrue();

        reference.clear();
        assertThat(reference.get()).isNull();
        assertThat(reference.refersTo(referent)).isFalse();
    }
}

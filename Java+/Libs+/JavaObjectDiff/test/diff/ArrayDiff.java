package diff;

import de.danielbechler.diff.ObjectDifferBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArrayDiff {

    /**
     * Doesn't support array diff.
     */
    @Test
    void array() {
        var s1 = new String[]{"a", "b"};
        var s2 = new String[]{"a", "c"};
        assertThatThrownBy(() -> ObjectDifferBuilder.buildDefault().compare(s1, s2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Couldn't find a differ for type: [Ljava.lang.String;");
    }
}

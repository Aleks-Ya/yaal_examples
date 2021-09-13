package diff;

import de.danielbechler.diff.ObjectDifferBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringDiff {
    @Test
    void strings() {
        var diff = ObjectDifferBuilder.buildDefault().compare("abc", "axc");
        assertThat(diff.isRootNode()).isTrue();
    }
}

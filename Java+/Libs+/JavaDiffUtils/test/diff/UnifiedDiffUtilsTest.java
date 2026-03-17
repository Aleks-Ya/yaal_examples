package diff;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UnifiedDiffUtilsTest {
    @Test
    void generateUnifiedDiff() {
        var source = "John drives a car";
        var target = "John see a car";
        var patch = DiffUtils.diffInline(source, target);
        var diff = UnifiedDiffUtils.generateUnifiedDiff("Text1", "Text2", List.of(source), patch, 0);
        assertThat(diff).containsOnly(
                "--- Text1",
                "+++ Text2",
                "@@ -6,1 +6,0 @@",
                "-drive",
                "@@ -12,0 +7,1 @@",
                "+ee"
        );
    }
}

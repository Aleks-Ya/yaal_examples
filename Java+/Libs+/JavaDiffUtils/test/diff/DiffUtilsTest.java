package diff;

import com.github.difflib.DiffUtils;
import org.junit.jupiter.api.Test;

class DiffUtilsTest {
    @Test
    void diffInline() {
        var source = "John drives a car";
        var target = "John see a car";
        var patch = DiffUtils.diffInline(source, target);
        System.out.println(patch);
    }
}

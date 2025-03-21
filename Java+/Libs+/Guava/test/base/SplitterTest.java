package base;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SplitterTest {
    @Test
    void splitFixedLength() {
        var splitter = Splitter.fixedLength(3);
        assertThat(splitter.split("12345678")).containsExactly("123", "456", "78");
    }
}
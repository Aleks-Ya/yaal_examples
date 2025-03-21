package text;

import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordUtilsTest {
    @Test
    void wrap() {
        var oneLineStr = "A long text typed in a one line";
        var multiLineStr = WordUtils.wrap(oneLineStr, 10);
        assertThat(multiLineStr).isEqualTo("""
                A long
                text typed
                in a one
                line""");
    }
}

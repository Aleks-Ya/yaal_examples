package lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void equalsIgnoreCase() {
        assertThat(StringUtils.equalsIgnoreCase(null, "")).isFalse();
    }

    @Test
    void isBlank() {
        assertThat(StringUtils.isBlank(null)).isTrue();
    }

    @Test
    void abbreviate() {
        var s = "An intentionally long boring string";
        assertThat(StringUtils.abbreviate(s, 22)).isEqualTo("An intentionally lo...");
        assertThat(StringUtils.abbreviate(s, "---", 22)).isEqualTo("An intentionally lo---");
        var marker = String.format("...(%d)", s.length());
        assertThat(StringUtils.abbreviate(s, marker, 22)).isEqualTo("An intentionall...(35)");
    }
}

package basic_utilites.using_avoiding_null;

import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringsTest {

    @Test
    void emptyToNull() {
        assertThat(Strings.emptyToNull("")).isNull();
        assertThat(Strings.emptyToNull("aa")).isNotNull();
    }

    @Test
    void isNullOrEmpty() {
        assertThat(Strings.isNullOrEmpty(null)).isTrue();
        assertThat(Strings.isNullOrEmpty("")).isTrue();
        assertThat(Strings.isNullOrEmpty("aa")).isFalse();
    }

    @Test
    void nullToEmpty() {
        assertThat(Strings.nullToEmpty(null)).isEqualTo("");
        assertThat(Strings.nullToEmpty("")).isEqualTo("");
        assertThat(Strings.nullToEmpty("aa")).isEqualTo("aa");
    }
}
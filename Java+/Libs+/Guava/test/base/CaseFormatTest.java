package base;

import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaseFormatTest {
    @Test
    void convert() {
        assertThat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "a_text_string")).isEqualTo("ATextString");
        assertThat(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "ATextString")).isEqualTo("a_text_string");
    }
}
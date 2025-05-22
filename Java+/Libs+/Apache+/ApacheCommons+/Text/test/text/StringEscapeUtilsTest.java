package text;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringEscapeUtilsTest {

    @Test
    void equalsIgnoreCase() {
        assertThat(StringEscapeUtils.escapeJson("""
                {"a": "b"}
                """
        )).isEqualTo("""
                {\\"a\\": \\"b\\"}\\n""");
    }

}

package lang.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Impossible to override.
 */
class EnumOverrideValueOfTest {

    @Test
    void test() {
        assertThat(Color.WHITE.getCode()).isEqualTo("w");
        assertThat(Color.valueOf("WHITE")).isEqualTo(Color.WHITE);
    }

    enum Color {
        WHITE("w"), BLACK("b");
        private final String code;

        Color(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}

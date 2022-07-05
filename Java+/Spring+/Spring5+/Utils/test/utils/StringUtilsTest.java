package utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    public void isEmpty() {
        assertThat(StringUtils.isEmpty("")).isTrue();
        assertThat(StringUtils.isEmpty(null)).isTrue();
        assertThat(StringUtils.isEmpty("a")).isFalse();
    }
}

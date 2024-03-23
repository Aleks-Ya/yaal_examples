package gptui;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogUtilsTest {
    @Test
    void shorten() {
        var maxSize = 30;
        assertThat(LogUtils.shorten((String) null))
                .isEqualTo(null);
        assertThat(LogUtils.shorten(""))
                .isEqualTo("")
                .hasSizeLessThanOrEqualTo(maxSize);
        assertThat(LogUtils.shorten("Not a long string"))
                .isEqualTo("Not a long string")
                .hasSizeLessThanOrEqualTo(maxSize);
        assertThat(LogUtils.shorten("A very long boring string about nothing interesting"))
                .isEqualTo("A very long boring stri...(51)")
                .hasSizeLessThanOrEqualTo(maxSize);
        assertThat(LogUtils.shorten("A very long boring string about nothing interesting"))
                .isEqualTo("A very long boring stri...(51)")
                .hasSizeLessThanOrEqualTo(maxSize);
    }
}
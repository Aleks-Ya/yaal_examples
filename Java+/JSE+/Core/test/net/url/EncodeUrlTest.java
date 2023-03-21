package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EncodeUrlTest {
    @Test
    void encode() {
        var encoded = URLEncoder.encode("https://www.ya.ru/space separated/abc 123", StandardCharsets.UTF_8);
        assertThat(encoded).isEqualTo("https%3A%2F%2Fwww.ya.ru%2Fspace+separated%2Fabc+123");
        assertThatThrownBy(() -> new URL(encoded))
                .isInstanceOf(MalformedURLException.class)
                .hasMessage("no protocol: https%3A%2F%2Fwww.ya.ru%2Fspace+separated%2Fabc+123");
    }
}

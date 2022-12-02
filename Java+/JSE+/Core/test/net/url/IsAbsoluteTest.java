package net.url;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class IsAbsoluteTest {

    @Test
    void isAbsolute() throws URISyntaxException {
        assertThat(new URI("https://ya.ru/search").isAbsolute()).isTrue();
        assertThat(new URI("/search").isAbsolute()).isFalse();
    }
}

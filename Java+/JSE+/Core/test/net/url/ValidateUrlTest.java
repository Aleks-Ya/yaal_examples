package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateUrlTest {

    @Test
    void correct() throws MalformedURLException {
        new URL("https://www.ya.ru");
        new URL("http", "www.ya.ru", "path");
    }

    @Test
    void noProtocol() {
        assertThatThrownBy(() -> new URL("www.ya.ru"))
                .isInstanceOf(MalformedURLException.class).hasMessage("no protocol: www.ya.ru");
    }

    @Test
    void hasProtocol() {
        var pattern = Pattern.compile("^\\w+://.+");
        assertThat(pattern.matcher("http://ya.ru/path?a=b&c=d").matches()).isTrue();
        assertThat(pattern.matcher("ya.ru/path?a=b&c=d").matches()).isFalse();
    }
}

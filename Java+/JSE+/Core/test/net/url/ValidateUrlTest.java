package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateUrlTest {

    @Test
    void correct() throws MalformedURLException {
        new URL("https://www.ya.ru");
        new URL("http", "www.ya.ru", "path");
    }

    @Test
    void noProtocol() {
        var e = assertThrows(MalformedURLException.class, () -> new URL("www.ya.ru"));
        assertThat(e.getMessage(), equalTo("no protocol: www.ya.ru"));
    }

    @Test
    void hasProtocol() {
        var pattern = Pattern.compile("^\\w+://.+");
        assertTrue(pattern.matcher("http://ya.ru/path?a=b&c=d").matches());
        assertFalse(pattern.matcher("ya.ru/path?a=b&c=d").matches());
    }
}

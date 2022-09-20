package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class RelativeUrlToAbsoluteTest {

    @Test
    void urlFromRoot() throws MalformedURLException {
        var baseUrl = new URL("https://ya.ru/search");//finished without "/"
        assertThat(new URL(baseUrl, "tail")).hasToString("https://ya.ru/tail");
        assertThat(new URL(baseUrl, "/tail")).hasToString("https://ya.ru/tail");
    }

    @Test
    void urlRelative() throws MalformedURLException {
        var baseUrl = new URL("https://ya.ru/search/");//finished with "/"
        assertThat(new URL(baseUrl, "tail")).hasToString("https://ya.ru/search/tail");
        assertThat(new URL(baseUrl, "/tail")).hasToString("https://ya.ru/tail");
    }

    @Test
    void fragment() throws MalformedURLException {
        var baseUrl = new URL("https://ya.ru/search");//finished without "/"
        assertThat(new URL(baseUrl, "#contacts")).hasToString("https://ya.ru/search#contacts");
    }
}

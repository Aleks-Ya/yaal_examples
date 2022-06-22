package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
    void uri() throws URISyntaxException {
        var context = new URI("https://ya.ru/search");
        assertThat(context.resolve(new URI("tail"))).hasToString("https://ya.ru/tail");
        assertThat(context.resolve(new URI("/tail"))).hasToString("https://ya.ru/tail");
        assertThat(context.resolve("tail")).hasToString("https://ya.ru/tail");
        assertThat(context.relativize(new URI("tail"))).hasToString("tail");
    }

    @Test
    void fragment() throws MalformedURLException {
        var baseUrl = new URL("https://ya.ru/search");//finished without "/"
        assertThat(new URL(baseUrl, "#contacts")).hasToString("https://ya.ru/search#contacts");
    }
}

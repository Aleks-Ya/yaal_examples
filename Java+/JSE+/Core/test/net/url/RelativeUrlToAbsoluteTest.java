package net.url;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

public class RelativeUrlToAbsoluteTest {

    @Test
    public void urlFromRoot() throws MalformedURLException {
        URL baseUrl = new URL("https://ya.ru/search");//finished without "/"
        assertThat(new URL(baseUrl, "tail"), hasToString("https://ya.ru/tail"));
        assertThat(new URL(baseUrl, "/tail"), hasToString("https://ya.ru/tail"));
    }

    @Test
    public void urlRelative() throws MalformedURLException {
        URL baseUrl = new URL("https://ya.ru/search/");//finished with "/"
        assertThat(new URL(baseUrl, "tail"), hasToString("https://ya.ru/search/tail"));
        assertThat(new URL(baseUrl, "/tail"), hasToString("https://ya.ru/tail"));
    }

    @Test
    public void uri() throws URISyntaxException {
        URI context = new URI("https://ya.ru/search");
        assertThat(context.resolve(new URI("tail")), hasToString("https://ya.ru/tail"));
        assertThat(context.resolve(new URI("/tail")), hasToString("https://ya.ru/tail"));
        assertThat(context.resolve("tail"), hasToString("https://ya.ru/tail"));
        assertThat(context.relativize(new URI("tail")), hasToString("tail"));
    }

    @Test
    public void fragment() throws MalformedURLException {
        URL baseUrl = new URL("https://ya.ru/search");//finished without "/"
        assertThat(new URL(baseUrl, "#contacts"), hasToString("https://ya.ru/search#contacts"));
    }
}

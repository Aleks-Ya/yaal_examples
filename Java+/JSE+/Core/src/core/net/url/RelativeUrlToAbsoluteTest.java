package core.net.url;

import org.junit.Test;

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
        MatcherAssert.assertThat(new URL(baseUrl, "tail"), Matchers.hasToString("https://ya.ru/tail"));
        MatcherAssert.assertThat(new URL(baseUrl, "/tail"), Matchers.hasToString("https://ya.ru/tail"));
    }

    @Test
    public void urlRelative() throws MalformedURLException {
        URL baseUrl = new URL("https://ya.ru/search/");//finished with "/"
        MatcherAssert.assertThat(new URL(baseUrl, "tail"), Matchers.hasToString("https://ya.ru/search/tail"));
        MatcherAssert.assertThat(new URL(baseUrl, "/tail"), Matchers.hasToString("https://ya.ru/tail"));
    }

    @Test
    public void uri() throws URISyntaxException {
        URI context = new URI("https://ya.ru/search");
        MatcherAssert.assertThat(context.resolve(new URI("tail")), Matchers.hasToString("https://ya.ru/tail"));
        MatcherAssert.assertThat(context.resolve(new URI("/tail")), Matchers.hasToString("https://ya.ru/tail"));
        MatcherAssert.assertThat(context.resolve("tail"), Matchers.hasToString("https://ya.ru/tail"));
        MatcherAssert.assertThat(context.relativize(new URI("tail")), Matchers.hasToString("tail"));
    }

    @Test
    public void fragment() throws MalformedURLException {
        URL baseUrl = new URL("https://ya.ru/search");//finished without "/"
        MatcherAssert.assertThat(new URL(baseUrl, "#contacts"), Matchers.hasToString("https://ya.ru/search#contacts"));
    }
}

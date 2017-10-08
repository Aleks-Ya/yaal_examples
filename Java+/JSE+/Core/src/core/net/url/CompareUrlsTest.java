package core.net.url;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotEquals;

public class CompareUrlsTest {

    @Test
    public void differentFragments() throws URISyntaxException {
        Assert.assertNotEquals(new URI("https://ya.ru/search#abc"), new URI("https://ya.ru/search#cde"));
    }
}

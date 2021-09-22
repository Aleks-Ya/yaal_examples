package net.url;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CompareUrlsTest {

    @Test
    void differentFragments() throws URISyntaxException {
        assertNotEquals(new URI("https://ya.ru/search#abc"), new URI("https://ya.ru/search#cde"));
    }
}

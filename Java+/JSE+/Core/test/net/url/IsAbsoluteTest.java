package net.url;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsAbsoluteTest {

    @Test
    public void isAbsolute() throws URISyntaxException {
        assertTrue(new URI("https://ya.ru/search").isAbsolute());
        assertFalse(new URI("/search").isAbsolute());
    }
}

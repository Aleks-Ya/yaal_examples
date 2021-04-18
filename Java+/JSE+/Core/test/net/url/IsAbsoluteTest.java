package net.url;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsAbsoluteTest {

    @Test
    public void isAbsolute() throws URISyntaxException {
        assertTrue(new URI("https://ya.ru/search").isAbsolute());
        assertFalse(new URI("/search").isAbsolute());
    }
}

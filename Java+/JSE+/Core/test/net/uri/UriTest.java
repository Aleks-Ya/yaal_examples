package net.uri;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class UriTest {

    @Test
    void resolve() {
        var uri = URI.create("https://ya.ru/search");
        assertThat(uri.resolve(URI.create("tail"))).hasToString("https://ya.ru/tail");
        assertThat(uri.resolve(URI.create("/tail"))).hasToString("https://ya.ru/tail");
        assertThat(uri.resolve("tail")).hasToString("https://ya.ru/tail");
    }

    @Test
    void relativize() {
        var uri = URI.create("https://ya.ru/search");
        assertThat(uri.relativize(URI.create("tail"))).hasToString("tail");
        assertThat(uri.relativize(URI.create("/tail"))).hasToString("/tail");
    }

    @Test
    void appendPath() {
        assertThat(URI.create(URI.create("https://ya.ru/search/") + "tail")).hasToString("https://ya.ru/search/tail");
        assertThat(URI.create(URI.create("https://ya.ru/search") + "/tail")).hasToString("https://ya.ru/search/tail");
    }

}

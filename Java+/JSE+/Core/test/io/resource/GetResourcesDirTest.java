package io.resource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Получить папку с ресурсами.
 */
class GetResourcesDirTest {

    @Test
    void classResourceEmpty() {
        var url2 = GetResourcesDirTest.class.getResource("");
        assertThat(url2.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classResourceDot() {
        var url1 = GetResourcesDirTest.class.getResource(".");
        assertThat(url1.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classLoaderResource() {
        var resource = ClassLoader.getSystemClassLoader().getResource(".");
        assertThat(resource).isNotNull();
        assertThat(resource.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/");
    }

    @Test
    void nullPath() {
        assertThatThrownBy(() -> GetResourcesDirTest.class.getResource(null))
                .isInstanceOf(NullPointerException.class);
    }
}

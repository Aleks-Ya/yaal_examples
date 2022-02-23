package io.resource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Получить папку с ресурсами.
 */
class GetResourcesDir {

    @Test
    void classResourceEmpty() {
        var url2 = GetResourcesDir.class.getResource("");
        assertThat(url2.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classResourceDot() {
        var url1 = GetResourcesDir.class.getResource(".");
        assertThat(url1.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/io/resource/");
    }

    @Test
    void classLoaderResource() {
        var resource = ClassLoader.getSystemClassLoader().getResource(".");
        assertNotNull(resource);
        assertThat(resource.toString()).endsWith("Java+/JSE+/Core/build/classes/java/test/");
    }

    @Test
    void nullPath() {
        assertThrows(NullPointerException.class, () -> GetResourcesDir.class.getResource(null));
    }
}

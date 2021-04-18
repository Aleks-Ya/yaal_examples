package io.resource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Получить папку с ресурсами.
 */
public class GetResourcesDir {

    @Test
    public void classResource() {
        var url1 = GetResourcesDir.class.getResource(".");
        var url2 = GetResourcesDir.class.getResource("");

        final var expResourcesDir = "file:/home/aleks/projects/learning/examples/Java+/JSE+/Core+/build/classes/test/resource/";
        assertEquals(expResourcesDir, url1.toString());
        assertEquals(expResourcesDir, url2.toString());
    }

    @Test
    public void classLoaderResource() {
        var resource = ClassLoader.getSystemClassLoader().getResource(".");

        final var expResourcesDir = "file:/home/aleks/projects/learning/examples/Java+/JSE+/Core+/build/classes/test/";
        assertNotNull(resource);
        assertEquals(expResourcesDir, resource.toString());
    }

    @Test
    public void nullPath() {
        assertThrows(NullPointerException.class, () -> GetResourcesDir.class.getResource(null));
    }
}

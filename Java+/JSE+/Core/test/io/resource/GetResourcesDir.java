package io.resource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test(expected = NullPointerException.class)
    public void nullPath() {
        GetResourcesDir.class.getResource(null);
    }
}

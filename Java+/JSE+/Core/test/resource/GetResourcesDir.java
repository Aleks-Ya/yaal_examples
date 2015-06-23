package resource;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Получить папку с ресурсами.
 */
public class GetResourcesDir {

    @Test
    public void classResource() {
        URL url1 = GetResourcesDir.class.getResource(".");
        URL url2 = GetResourcesDir.class.getResource("");

        final String expResourcesDir = "file:/home/aleks/projects/learning/examples/Java+/JSE+/Core+/build/classes/test/resource/";
        assertEquals(expResourcesDir, url1.toString());
        assertEquals(expResourcesDir, url2.toString());
    }

    @Test
    public void classLoaderResource() {
        URL resource = ClassLoader.getSystemClassLoader().getResource(".");

        final String expResourcesDir = "file:/home/aleks/projects/learning/examples/Java+/JSE+/Core+/build/classes/test/";
        assertNotNull(resource);
        assertEquals(expResourcesDir, resource.toString());
    }

    @Test(expected = NullPointerException.class)
    public void nullPath() {
        GetResourcesDir.class.getResource(null);
    }
}

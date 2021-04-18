package lang.reflection.classloader;

import org.junit.jupiter.api.Test;
//import sun.net.spi.nameservice.dns.DNSNameService;

import static org.junit.Assert.assertNull;

public class BuiltInClassloaderTest {

    /**
     * Boostrap Classloader is null.
     */
    @Test
    public void bootstrapClassLoader() {
        assertNull(StringBuilder.class.getClassLoader());
        assertNull(Integer.class.getClassLoader());
        assertNull(String.class.getClassLoader());
        assertNull(int[].class.getClassLoader());
        assertNull(Integer[].class.getClassLoader());
    }

    @Test
    public void extensionsClassLoader() {
//        ClassLoader extensionClassLoader = DNSNameService.class.getClassLoader();
//        assertNull(extensionClassLoader.getParent());
    }

    @Test
    public void systemClassLoader() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        assertNull(extensionClassLoader.getParent());
    }
}
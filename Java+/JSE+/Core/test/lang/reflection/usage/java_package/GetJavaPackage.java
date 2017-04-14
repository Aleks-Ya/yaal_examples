package lang.reflection.usage.java_package;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Получить пакет Java в коде.
 */
public class GetJavaPackage {
    @Test
    public void test() throws Exception {
        Package pack = Package.getPackage("java.lang");
        assertNotNull(pack);
    }
}

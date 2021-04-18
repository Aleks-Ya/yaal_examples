package lang.reflection.usage.java_package;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

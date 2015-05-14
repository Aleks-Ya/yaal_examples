package java_package;

import org.junit.Test;

/**
 * Получить пакет Java в коде.
 */
public class GetJavaPackage {
    @Test
    public void test() throws Exception {
        Package pack = Package.getPackage("java.lang");
        pack.getClass();

    }
}

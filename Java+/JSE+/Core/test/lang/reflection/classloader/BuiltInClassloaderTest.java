package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuiltInClassloaderTest {

    /**
     * Boostrap Classloader is null.
     */
    @Test
    void bootstrapClassLoader() {
        assertThat(StringBuilder.class.getClassLoader()).isNull();
        assertThat(Integer.class.getClassLoader()).isNull();
        assertThat(String.class.getClassLoader()).isNull();
        assertThat(int[].class.getClassLoader()).isNull();
        assertThat(Integer[].class.getClassLoader()).isNull();
    }

    @Test
    void extensionsClassLoader() {
//        ClassLoader extensionClassLoader = DNSNameService.class.getClassLoader();
//        assertThat(extensionClassLoader.getParent());
    }

    @Test
    void systemClassLoader() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        assertThat(extensionClassLoader.getParent()).isNull();
    }
}
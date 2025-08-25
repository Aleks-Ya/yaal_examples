package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClassLoaderToStringTest {
    /**
     * Boostrap ClassLoader is null.
     */
    @Test
    void bootstrapClassLoader() {
        var bootstrapClassLoader = StringBuilder.class.getClassLoader();
        var str = classLoaderToString(bootstrapClassLoader);
        assertThat(str).isEqualTo("BootstrapClassLoader");
    }

    /**
     * Platform ClassLoader (Java ≥9) or Extension ClassLoader (Java ≤8).
     */
    @Test
    void platformClassLoader() {
        var platformClassLoader = ClassLoader.getPlatformClassLoader();
        var str = classLoaderToString(platformClassLoader);
        assertThat(str).isEqualTo("PlatformClassLoader");
    }

    @Test
    void systemClassLoader() {
        var systemClassLoader = ClassLoader.getSystemClassLoader();
        var str = classLoaderToString(systemClassLoader);
        assertThat(str).isEqualTo("AppClassLoader");
    }

    private static String classLoaderToString(ClassLoader bootstrapClassLoader) {
        return bootstrapClassLoader != null ? bootstrapClassLoader.getClass().getSimpleName() : "BootstrapClassLoader";
    }
}
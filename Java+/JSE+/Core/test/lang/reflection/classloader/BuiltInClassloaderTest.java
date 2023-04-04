package lang.reflection.classloader;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class BuiltInClassloaderTest {
    /**
     * Boostrap ClassLoader is null.
     */
    @Test
    void bootstrapClassLoader() {
        assertThat(StringBuilder.class.getClassLoader()).isNull();
        assertThat(Integer.class.getClassLoader()).isNull();
        assertThat(String.class.getClassLoader()).isNull();
        assertThat(int[].class.getClassLoader()).isNull();
        assertThat(Integer[].class.getClassLoader()).isNull();
    }

    /**
     * Platform ClassLoader (Java ≥9) or Extension ClassLoader (Java ≤8).
     */
    @Test
    void platformClassLoader() {
        var platformClassLoader = ClassLoader.getPlatformClassLoader();
        assertThat(platformClassLoader).isNotNull();
        assertThat(platformClassLoader.getParent()).isNull();
        assertThat(Connection.class.getClassLoader()).isEqualTo(platformClassLoader);
    }

    @Test
    void systemClassLoader() {
        var systemClassLoader = ClassLoader.getSystemClassLoader();
        assertThat(systemClassLoader).isNotNull();
        assertThat(systemClassLoader.getParent()).isEqualTo(ClassLoader.getPlatformClassLoader());
        assertThat(BuiltInClassloaderTest.class.getClassLoader()).isEqualTo(systemClassLoader);
    }
}
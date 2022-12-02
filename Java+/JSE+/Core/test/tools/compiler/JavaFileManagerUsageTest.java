package tools.compiler;

import org.junit.jupiter.api.Test;

import javax.tools.JavaFileManager;
import javax.tools.ToolProvider;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование JavaFileManager.
 */
class JavaFileManagerUsageTest {

    @Test
    void getInstance() {
        var compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        assertThat(fileManager).isNotNull();
    }
}

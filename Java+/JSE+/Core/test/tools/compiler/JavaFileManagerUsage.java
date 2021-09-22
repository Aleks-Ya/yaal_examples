package tools.compiler;

import org.junit.jupiter.api.Test;

import javax.tools.JavaFileManager;
import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Использование JavaFileManager.
 */
class JavaFileManagerUsage {

    @Test
    void getInstance() {
        var compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        assertNotNull(fileManager);
    }
}

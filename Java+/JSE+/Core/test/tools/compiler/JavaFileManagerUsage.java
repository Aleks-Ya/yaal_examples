package tools.compiler;

import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.ToolProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Использование JavaFileManager.
 */
public class JavaFileManagerUsage {

    @Test
    public void getInstance() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        assertNotNull(fileManager);
    }
}

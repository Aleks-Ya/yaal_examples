package core.tools.compiler;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.ToolProvider;

import static org.junit.Assert.assertNotNull;

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

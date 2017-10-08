package core.io.file.temp;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

/**
 * Создание временных папок и файлов в Java.
 */
public class TempFiles {
    /**
     * java.io.File#createTempFile
     */
    @Test
    public void tempFile() throws IOException {
        File f1 = File.createTempFile("prefix-file_", ".suffix");
        File f2 = File.createTempFile("prefix-file_", null);// ".tmp" suffix by default

        f1.deleteOnExit();
        f2.deleteOnExit();

        out.println(f1);
        out.println(f2);
    }

    @Test
    public void systemTempDir() {
        String tmpDirStr = System.getProperty("java.io.tmpdir");
        File tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }
}
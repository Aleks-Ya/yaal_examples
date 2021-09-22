package io.file.temp;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

/**
 * Создание временных папок и файлов в Java.
 */
class TempFiles {
    /**
     * java.io.File#createTempFile
     */
    @Test
    void tempFile() throws IOException {
        var f1 = File.createTempFile("prefix-file_", ".suffix");
        var f2 = File.createTempFile("prefix-file_", null);// ".tmp" suffix by default

        f1.deleteOnExit();
        f2.deleteOnExit();

        out.println(f1);
        out.println(f2);
    }

    @Test
    void systemTempDir() {
        var tmpDirStr = System.getProperty("java.io.tmpdir");
        var tmpDir = new File(tmpDirStr);
        System.out.println("Temp dir: " + tmpDir.getAbsolutePath());
    }
}
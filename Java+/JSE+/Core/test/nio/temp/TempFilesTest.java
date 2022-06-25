package nio.temp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.System.out;

/**
 * Создание временных папок и файлов в Java.
 */
class TempFilesTest {

    /**
     * java.nio.file.Files#createTempFile
     */
    @Test
    void tempFiles() throws IOException {
        var f1 = Files.createTempFile("prefix-files_", ".suffix");
        var f2 = Files.createTempFile("prefix-files_", null);// ".tmp" suffix by default

        f1.toFile().deleteOnExit();
        f2.toFile().deleteOnExit();

        out.println(f1);
        out.println(f2);
    }

    @Test
    void tempFolders() throws IOException {
        var tmpDir = Files.createTempDirectory("prefix-folder_");
        tmpDir.toFile().deleteOnExit();
        out.println(tmpDir);
    }
}
package nio.temp;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

/**
 * Создание временных папок и файлов в Java.
 */
public class TempFiles {

    /**
     * java.nio.file.Files#createTempFile
     */
    @Test
    public void tempFiles() throws IOException {
        Path f1 = Files.createTempFile("prefix-files_", ".suffix");
        Path f2 = Files.createTempFile("prefix-files_", null);// ".tmp" suffix by default

        f1.toFile().deleteOnExit();
        f2.toFile().deleteOnExit();

        out.println(f1);
        out.println(f2);
    }

    @Test
    public void tempFolders() throws IOException {
        Path tmpDir = Files.createTempDirectory("prefix-folder_");
        tmpDir.toFile().deleteOnExit();
        out.println(tmpDir);
    }
}
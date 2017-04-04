import org.hamcrest.io.FileMatchers;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

/**
 * Проверка файлов.
 */
public class FileAssert {
    @Test
    public void existingFile() throws IOException {
        File f = Files.createTempFile("pref", "suffix").toFile();
        f.deleteOnExit();
        assertThat(f, FileMatchers.anExistingFile());
    }

    @Test
    public void canonicalPath() throws IOException {
        File f = Files.createTempFile("pref", "suffix").toFile();
        f.deleteOnExit();
        assertThat(f, FileMatchers.aFileWithCanonicalPath(endsWith("suffix")));
    }
}
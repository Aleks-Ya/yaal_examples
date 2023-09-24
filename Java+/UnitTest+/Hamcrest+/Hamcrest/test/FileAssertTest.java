import org.hamcrest.io.FileMatchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;

/**
 * Проверка файлов.
 */
class FileAssertTest {
    @Test
    void existingFile() throws IOException {
        var f = Files.createTempFile("pref", "suffix").toFile();
        f.deleteOnExit();
        assertThat(f, FileMatchers.anExistingFile());
    }

    @Test
    void canonicalPath() throws IOException {
        var f = Files.createTempFile("pref", "suffix").toFile();
        f.deleteOnExit();
        assertThat(f, FileMatchers.aFileWithCanonicalPath(endsWith("suffix")));
    }
}
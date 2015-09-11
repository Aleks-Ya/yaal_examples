import org.hamcrest.io.FileMatchers;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertThat;

/**
 * Проверка файлов.
 */
public class FileAssert {
    @Test
    public void testName() throws Exception {
        File f = Files.createTempFile("pref", "suffix").toFile();
        f.deleteOnExit();

        assertThat(f, FileMatchers.anExistingFile());
    }
}
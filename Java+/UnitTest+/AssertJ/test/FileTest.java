import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Docs: https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#file-content-string-assertions
 */
public class FileTest {

    private static File createTmpFile(String content) throws IOException {
        File f = File.createTempFile(FileTest.class.getSimpleName(), ".tmp");
        Files.write(f.toPath(), content.getBytes());
        return f;
    }

    @Test
    public void assertFileContent() throws IOException {
        String content = "Hello Big World";
        File f = createTmpFile(content);
        assertThat(f)
                .exists()
                .isFile()
                .canRead()
                .canWrite()
                .hasContent(content);
        assertThat(Assertions.contentOf(f))
                .startsWith("Hello")
                .contains("Big")
                .endsWith("World");
    }

    @Test
    public void filesContentsAreEqual() throws IOException {
        String content = "Hello Big World";
        File actFile = createTmpFile(content);
        File expFile = createTmpFile(content);
        assertThat(actFile).hasSameContentAs(expFile);
    }
}

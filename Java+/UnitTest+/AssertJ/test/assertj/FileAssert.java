package assertj;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Docs: https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#file-content-string-assertions
 */
class FileAssert {

    private static File createTmpFile(byte[] content) throws IOException {
        var f = File.createTempFile(FileAssert.class.getSimpleName(), ".tmp");
        Files.write(f.toPath(), content);
        return f;
    }

    private static File createTmpFile(String content) throws IOException {
        return createTmpFile(content.getBytes());
    }

    @Test
    void assertFileContent() throws IOException {
        var content = "Hello Big World";
        var f = createTmpFile(content);
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
    void filesContentsAreEqualText() throws IOException {
        var content = "Hello Big World";
        var actFile = createTmpFile(content);
        var expFile = createTmpFile(content);
        assertThat(actFile).hasSameTextualContentAs(expFile);
    }

    @Test
    void filesContentsAreEqualBinary() throws IOException {
        var content = new byte[]{1, 3, 5, 7};
        var actFile = createTmpFile(content);
        var expFile = createTmpFile(content);
        assertThat(actFile).hasSameBinaryContentAs(expFile);
    }
}

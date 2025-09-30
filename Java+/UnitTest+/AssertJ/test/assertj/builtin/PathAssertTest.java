package assertj.builtin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PathAssertTest {

    private static Path createTmpFile(byte[] content) throws IOException {
        var path = Files.createTempFile(PathAssertTest.class.getSimpleName(), ".tmp");
        Files.write(path, content);
        return path;
    }

    private static Path createTmpFile(String content) throws IOException {
        return createTmpFile(content.getBytes());
    }

    @Test
    void assertFileContent() throws IOException {
        var content = "Hello Big World";
        var path = createTmpFile(content);
        assertThat(path)
                .exists()
                .isNotEmptyFile()
                .isReadable()
                .isWritable()
                .hasContent(content);
    }

    @Test
    void filesContentsAreEqualText() throws IOException {
        var content = "Hello Big World";
        var actPath = createTmpFile(content);
        var expPath = createTmpFile(content);
        assertThat(actPath).hasSameTextualContentAs(expPath);
    }

    @Test
    void filesContentsAreEqualBinary() throws IOException {
        var content = new byte[]{1, 3, 5, 7};
        var actPath = createTmpFile(content);
        var expPath = createTmpFile(content);
        assertThat(actPath).hasSameBinaryContentAs(expPath);
    }
}

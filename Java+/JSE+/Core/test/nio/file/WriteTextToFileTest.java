package nio.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;

class WriteTextToFileTest {

    @Test
    void writeLinesToFile() throws IOException {
        var path = Files.createTempFile(WriteTextToFileTest.class.getSimpleName(), ".tmp");
        var lines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(path, lines, Charset.defaultCharset());
    }

    @Test
    void overwriteExistingFile() throws IOException {
        var path = Files.createTempFile(WriteTextToFileTest.class.getSimpleName(), ".tmp");
        Files.writeString(path, "abc", Charset.defaultCharset(), TRUNCATE_EXISTING);
        Files.writeString(path, "xyz", Charset.defaultCharset(), TRUNCATE_EXISTING);
        assertThat(Files.readString(path)).isEqualTo("xyz");
    }

    @Test
    void appendExistingFile() throws IOException {
        var path = Files.createTempFile(WriteTextToFileTest.class.getSimpleName(), ".tmp");
        Files.writeString(path, "abc", Charset.defaultCharset(), TRUNCATE_EXISTING);
        Files.writeString(path, "xyz", Charset.defaultCharset(), APPEND);
        assertThat(Files.readString(path)).isEqualTo("abcxyz");
    }
}
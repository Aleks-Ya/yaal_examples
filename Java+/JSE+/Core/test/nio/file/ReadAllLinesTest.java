package nio.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Чтение целого файла в одно строку с помощью Files#readAllLines()
 */
class ReadAllLinesTest {
    @Test
    void files() throws IOException {
        //write
        var p = Files.createTempFile("prefix-file_", ".suffix");
        var expLines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, expLines, Charset.defaultCharset());

        //read
        var actLines = Files.readAllLines(p, Charset.defaultCharset());
        assertThat(actLines).isEqualTo(expLines);
    }
}
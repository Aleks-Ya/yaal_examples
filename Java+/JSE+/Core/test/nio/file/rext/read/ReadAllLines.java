package nio.file.rext.read;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Чтение целого файла в одно строку с помощью Files#readAllLines()
 */
class ReadAllLines {
    @Test
    void files() throws IOException {
        //write
        var p = Files.createTempFile("prefix-file_", ".suffix");
        var expLines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, expLines, Charset.defaultCharset());

        //read
        var actLines = Files.readAllLines(p, Charset.defaultCharset());
        assertEquals(expLines, actLines);
    }
}
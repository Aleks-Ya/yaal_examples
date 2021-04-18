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
public class ReadAllLines {
    @Test
    public void files() throws IOException {
        //write
        Path p = Files.createTempFile("prefix-file_", ".suffix");
        List<String> expLines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, expLines, Charset.defaultCharset());

        //read
        List<String> actLines = Files.readAllLines(p, Charset.defaultCharset());
        assertEquals(expLines, actLines);
    }
}
package nio.file.rext.write;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

import static java.lang.System.out;

class WriteTextToFileTest {

    @Test
    void files() throws IOException {
        var p = Files.createTempFile("prefix-file_", ".suffix");
        out.println(p);

        var lines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, lines, Charset.defaultCharset());
    }
}
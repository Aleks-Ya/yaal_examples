package core.nio.file.rext.write;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class WriteTextToFile {

    @Test
    public void files() throws IOException {
        Path p = Files.createTempFile("prefix-file_", ".suffix");
        out.println(p);

        List<String> lines = Arrays.asList("FirstLine", "SecondLine");
        Files.write(p, lines, Charset.defaultCharset());
    }
}
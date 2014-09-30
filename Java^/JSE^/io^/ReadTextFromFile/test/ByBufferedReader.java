import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Чтение файла с помощью BufferedReader.
 */
public class ByBufferedReader {
    private static File file;
    private static final List<String> expLines = Arrays.asList("FirstLine", "SecondLine");

    @BeforeClass
    public static void setUp() throws Exception {
        Path p = File.createTempFile("prefix-file_", ".suffix").toPath();
        Files.write(p, expLines);
        file = p.toFile();
        file.deleteOnExit();
    }

    /**
     * Чтение файла по одной строке.
     */
    @Test
    public void files() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        assertEquals("FirstLine\nSecondLine\n", sb.toString());
    }
}
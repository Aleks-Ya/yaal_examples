package core.io.file.text.read;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Чтение файла с помощью BufferedReader.
 */
public class ByBufferedReader {
    private static File file;
    private static final List<String> expLines = Arrays.asList("FirstLine", "SecondLine");
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        file = File.createTempFile(ByRandomAccessFile.class.getSimpleName(), ".tmp");
        file.deleteOnExit();
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            expLines.forEach(out::print);
        }
    }

    /**
     * Чтение файла по одной строке.
     */
    @Test
    public void lineByLine() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();
        Assert.assertEquals("FirstLine\nSecondLine\n", sb.toString());
    }
}
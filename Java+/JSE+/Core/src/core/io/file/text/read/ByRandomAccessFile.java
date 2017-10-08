package core.io.file.text.read;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Чтение тектового файла с помощью RandomAccesFile.
 */
public class ByRandomAccessFile {
    private static File file;
    private static final List<String> expLines = Arrays.asList("FirstLine", "SecondLine");

    @BeforeClass
    public static void setUp() throws Exception {
        file = File.createTempFile(ByRandomAccessFile.class.getSimpleName(), ".tmp");
        file.deleteOnExit();
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            expLines.forEach(out::print);
        }
    }

    @Test
    public void lineByLine() throws IOException {
        StringBuilder lines = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                lines.append(line);
            }
        }

        Assert.assertEquals("FirstLineSecondLine", lines.toString());
    }
}
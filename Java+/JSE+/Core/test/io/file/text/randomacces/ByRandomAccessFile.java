package io.file.text.randomacces;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path p = File.createTempFile("prefix-file_", ".suffix").toPath();
        Files.write(p, expLines, Charset.defaultCharset());
        file = p.toFile();
        file.deleteOnExit();
    }

    @Test
    public void lineByLine() throws IOException {
        String lines = "";
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                lines += line;
            }
        }

        assertEquals("FirstLineSecondLine", lines);
    }
}
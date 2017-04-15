package io.file.text.read;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
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
        Files.write(p, expLines, Charset.defaultCharset());
        file = p.toFile();
        file.deleteOnExit();
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
        assertEquals("FirstLine\nSecondLine\n", sb.toString());
    }

    /**
     * Чтение файла через Stream.
     * since Java 8
     */
    @Test
    public void wholeFile() throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        Stream stream = reader.lines();
//        assertArrayEquals(expLines.toArray(), stream.toArray());
//        reader.close();
    }
}
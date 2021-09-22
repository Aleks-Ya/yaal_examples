package io.file.text.read;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Чтение тектового файла с помощью RandomAccesFile.
 */
class ByRandomAccessFile {
    private static File file;
    private static final List<String> expLines = Arrays.asList("FirstLine", "SecondLine");

    @BeforeAll
    static void setUp() throws Exception {
        file = File.createTempFile(ByRandomAccessFile.class.getSimpleName(), ".tmp");
        file.deleteOnExit();
        try (var out = new PrintStream(new FileOutputStream(file))) {
            expLines.forEach(out::print);
        }
    }

    @Test
    void lineByLine() throws IOException {
        var lines = new StringBuilder();
        try (var raf = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                lines.append(line);
            }
        }
        assertEquals("FirstLineSecondLine", lines.toString());
    }
}
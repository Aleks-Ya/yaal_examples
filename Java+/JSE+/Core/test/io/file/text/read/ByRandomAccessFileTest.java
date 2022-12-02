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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Чтение тектового файла с помощью RandomAccesFile.
 */
class ByRandomAccessFileTest {
    private static final List<String> expLines = Arrays.asList("FirstLine", "SecondLine");
    private static File file;

    @BeforeAll
    static void setUp() throws Exception {
        file = File.createTempFile(ByRandomAccessFileTest.class.getSimpleName(), ".tmp");
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
        assertThat(lines).hasToString("FirstLineSecondLine");
    }
}
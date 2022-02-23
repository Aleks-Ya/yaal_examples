package io.file.text.read;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Чтение файла с помощью BufferedReader.
 */
class ByBufferedReader {
    private static final List<String> expLines = List.of("FirstLine", "SecondLine");
    private static File file;

    @BeforeEach
    void setUp() throws Exception {
        file = File.createTempFile(ByRandomAccessFile.class.getSimpleName(), ".tmp");
        file.deleteOnExit();
        Files.write(file.toPath(), expLines);
    }

    /**
     * Чтение файла по одной строке.
     */
    @Test
    void lineByLine() throws IOException {
        var sb = new StringBuilder();
        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
        }
        assertThat(sb.toString()).isEqualTo("FirstLine\nSecondLine\n");
    }
}
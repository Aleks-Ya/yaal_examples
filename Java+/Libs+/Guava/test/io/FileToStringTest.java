package io;

import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

class FileToStringTest {

    @Test
    void readFileToString() throws IOException {
        var f = java.nio.file.Files.createTempFile(FileToStringTest.class.getName() + "_", ".tmp");
        var expContent = "abcd";
        java.nio.file.Files.write(f, expContent.getBytes());

        var actFile = f.toFile();
        var content = Files.asCharSource(actFile, Charset.defaultCharset()).read();

        assertThat(content).isEqualTo(expContent);
    }
}
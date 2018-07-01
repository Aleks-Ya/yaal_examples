package io;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileToStringTest {

    @Test
    public void readFileToString() throws IOException {
        Path f = java.nio.file.Files.createTempFile(FileToStringTest.class.getName() + "_", ".tmp");
        String expContent = "abcd";
        java.nio.file.Files.write(f, expContent.getBytes());

        File actFile = f.toFile();
        String content = Files.asCharSource(actFile, Charset.defaultCharset()).read();

        assertThat(content, equalTo(expContent));
    }
}
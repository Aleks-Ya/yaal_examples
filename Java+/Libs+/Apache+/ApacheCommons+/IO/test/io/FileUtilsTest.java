package io;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilsTest {

    private File outFile;

    @BeforeEach
    void setUp() throws Exception {
        outFile = Files.createTempFile("fileUtilsUse_", ".tmp").toFile();
        outFile.deleteOnExit();
    }

    @Test
    void stringToFile() throws IOException {
        var expected = "Hey, file!";
        FileUtils.writeStringToFile(outFile, expected, defaultCharset());
        var actual = FileUtils.readFileToString(outFile, defaultCharset());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void inputStreamToFile() throws IOException {
        byte[] expected = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(expected);
        FileUtils.copyInputStreamToFile(is, outFile);
        var actual = FileUtils.readFileToByteArray(outFile);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void readFileToString() throws IOException {
        var expected = "Hey, file!";
        FileUtils.writeStringToFile(outFile, expected, defaultCharset());
        var actual = FileUtils.readFileToString(outFile, defaultCharset());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void readFileToString_FileNotFound() {
        var file = new File("/tmp/absent.txt");
        assertThatThrownBy(() -> FileUtils.readFileToString(file, defaultCharset()))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage("%s (No such file or directory)", file.getAbsolutePath());
    }
}
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        FileUtils.writeStringToFile(outFile, expected, Charset.defaultCharset());
        var actual = FileUtils.readFileToString(outFile, Charset.defaultCharset());
        assertEquals(expected, actual);
    }

    @Test
    void inputStreamToFile() throws IOException {
        byte[] expected = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(expected);
        FileUtils.copyInputStreamToFile(is, outFile);
        var actual = FileUtils.readFileToByteArray(outFile);
        assertThat(actual, equalTo(expected));
    }
}
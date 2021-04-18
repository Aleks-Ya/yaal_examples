import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileUtilsTest {

    private File outFile;

    @BeforeEach
    public void setUp() throws Exception {
        outFile = Files.createTempFile("fileUtilsUse_", ".tmp").toFile();
        outFile.deleteOnExit();
    }

    @Test
    public void stringToFile() throws IOException {
        String expected = "Hey, file!";
        FileUtils.writeStringToFile(outFile, expected);
        String actual = FileUtils.readFileToString(outFile);
        assertEquals(expected, actual);
    }

    @Test
    public void inputStreamToFile() throws IOException {
        byte[] expected = {1, 2, 3};
        InputStream is = new ByteArrayInputStream(expected);
        FileUtils.copyInputStreamToFile(is, outFile);
        byte[] actual = FileUtils.readFileToByteArray(outFile);
        assertThat(actual, equalTo(expected));
    }
}
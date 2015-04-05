import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class FileUtilsUse {

    @Test
    public void writeStringToFile() throws IOException {
        File f = Files.createTempFile("fileUtilsUse_", ".tmp").toFile();
        f.deleteOnExit();
        String expected = "Hey, file!";

        FileUtils.writeStringToFile(f, expected);

        String actual = FileUtils.readFileToString(f);
        assertEquals(expected, actual);
    }
}
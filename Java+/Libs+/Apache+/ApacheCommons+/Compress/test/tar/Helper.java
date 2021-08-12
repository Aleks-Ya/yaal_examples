package tar;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Helper {
    public static void assertFileContent(File outDir, File expDir, String file) throws IOException {
        assertTrue(IOUtils.contentEquals(new FileInputStream(new File(outDir, file)), new FileInputStream(new File(expDir, file))));
    }
}

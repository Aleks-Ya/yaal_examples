package tar;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class Helper {
    public static void assertFileContent(File outDir, File expDir, String file) throws IOException {
        assertThat(IOUtils.contentEquals(new FileInputStream(new File(outDir, file)), new FileInputStream(new File(expDir, file))))
                .isTrue();
    }
}

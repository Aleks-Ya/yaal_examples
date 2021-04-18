package zip4j;

import net.lingala.zip4j.ZipFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateZipTest {
    @Test
    public void create() throws IOException {
        File notExistZipFile = Paths.get(
                Files.createTempDirectory(CreateZipTest.class.getSimpleName()).toString(), "my.zip").toFile();

        File txtFile1 = Files.createTempFile(CreateZipTest.class.getSimpleName(), "txt").toFile();
        File txtFile2 = Files.createTempFile(CreateZipTest.class.getSimpleName(), "txt").toFile();

        ZipFile zip = new ZipFile(notExistZipFile);
        zip.addFile(txtFile1);
        zip.addFile(txtFile2);
    }
}

package zip4j;

import net.lingala.zip4j.ZipFile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class CreateZipTest {
    @Test
    void create() throws IOException {
        var notExistZipFile = Paths.get(
                Files.createTempDirectory(CreateZipTest.class.getSimpleName()).toString(), "my.zip").toFile();

        var txtFile1 = Files.createTempFile(CreateZipTest.class.getSimpleName(), "txt").toFile();
        var txtFile2 = Files.createTempFile(CreateZipTest.class.getSimpleName(), "txt").toFile();

        var zip = new ZipFile(notExistZipFile);
        zip.addFile(txtFile1);
        zip.addFile(txtFile2);
    }
}

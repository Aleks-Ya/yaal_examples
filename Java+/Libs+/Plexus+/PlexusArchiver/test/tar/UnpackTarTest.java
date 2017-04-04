package tar;

import org.codehaus.plexus.archiver.tar.TarUnArchiver;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

public class UnpackTarTest {

    @Test
    public void wholeTarAtOnce() throws IOException {
        File srcFile = new File(getClass().getResource("my.tar").getFile());
        File destDir = Files.createTempDirectory("UnpackTarTest_").toFile();
        destDir.deleteOnExit();

        final TarUnArchiver ua = new TarUnArchiver();
        ua.setSourceFile(srcFile);
        ua.enableLogging(new ConsoleLogger(Logger.LEVEL_DEBUG, "console_logger"));
        ua.setDestDirectory(destDir);
        ua.extract();
        assertThat(destDir.list(), arrayContaining("mytar"));
    }
}
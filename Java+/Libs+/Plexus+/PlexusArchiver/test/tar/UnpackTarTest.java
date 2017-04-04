package tar;

import org.codehaus.plexus.archiver.tar.TarUnArchiver;
import org.codehaus.plexus.components.io.fileselectors.FileSelector;
import org.codehaus.plexus.components.io.fileselectors.IncludeExcludeFileSelector;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UnpackTarTest {
    private File destDir;
    private TarUnArchiver ua;

    @Before
    public void setUp() throws Exception {
        File srcFile = new File(getClass().getResource("my.tar").getFile());
        destDir = Files.createTempDirectory("UnpackTarTest_").toFile();
        destDir.deleteOnExit();

        ua = new TarUnArchiver();
        ua.setSourceFile(srcFile);
        ua.enableLogging(new ConsoleLogger(Logger.LEVEL_DEBUG, "console_logger"));
        ua.setDestDirectory(destDir);
    }

    @Test
    public void wholeTarAtOnce() throws IOException {
        ua.extract();
        assertThat(destDir.list(), arrayContaining("mytar"));
    }

    @Test
    public void fileSelector() throws IOException {
        IncludeExcludeFileSelector selector = new IncludeExcludeFileSelector();
        selector.setIncludes(new String[]{"mytar\\subdir\\subdir.txt"});

        ua.setFileSelectors(new FileSelector[]{selector});
        ua.extract();

        File[] level0 = destDir.listFiles();
        assertThat(level0, arrayWithSize(1));
        assertThat(level0[0].toString(), containsString("mytar"));
        File[] level1 = level0[0].listFiles();
        assertThat(level1, arrayWithSize(1));
        File[] level2 = level1[0].listFiles();
        assertThat(level2, arrayWithSize(1));
        assertThat(level2[0].toString(), containsString("subdir.txt"));


    }
}
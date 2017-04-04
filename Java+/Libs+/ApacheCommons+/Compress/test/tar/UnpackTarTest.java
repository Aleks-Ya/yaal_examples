package tar;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UnpackTarTest {

    @Test
    public void readEntries() throws IOException {
        InputStream is = getClass().getResourceAsStream("mytar.tar");
        TarArchiveInputStream tar = new TarArchiveInputStream(is);

        TarArchiveEntry entry = tar.getNextTarEntry();
        String name = entry.getName();
        assertThat(name, equalTo("mytar/"));
    }

}
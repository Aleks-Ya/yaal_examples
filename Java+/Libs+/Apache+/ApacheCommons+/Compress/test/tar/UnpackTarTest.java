package tar;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class UnpackTarTest {

    @Test
    void readEntries() throws IOException {
        var is = getClass().getResourceAsStream("mytar.tar");
        var tar = new TarArchiveInputStream(is);

        var entry = tar.getNextTarEntry();
        var name = entry.getName();
        assertThat(name, equalTo("mytar/"));
    }

}
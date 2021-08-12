package tar;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnpackTar {

    @Test
    void useTarArchiveInputStream() throws IOException {
        var is = getClass().getResourceAsStream("mytar.tar");
        var tar = new TarArchiveInputStream(is);

        var entry = tar.getNextTarEntry();
        var name = entry.getName();
        assertThat(name, equalTo("mytar/"));
    }

    @Test
    void useArchiveInputStream() throws IOException, ArchiveException {
        var outDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
        System.out.println("outDir: " + outDir.getAbsolutePath());
        var is = getClass().getResourceAsStream("mytar.tar");
        try (var ais = new ArchiveStreamFactory().createArchiveInputStream(is)) {
            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                if (!ais.canReadEntryData(entry)) {
                    continue;
                }
                var name = new File(outDir, entry.getName()).getAbsolutePath();
                var f = new File(name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    var parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (var o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(ais, o);
                    }
                }
            }
        }

        var expDir = ResourceUtil.resourceToFile(getClass(), "mytar.tar.extracted/mytar/content.txt").getParentFile().getParentFile();
        assertFileContent(outDir, expDir, "mytar/content.txt");
        assertFileContent(outDir, expDir, "mytar/subdir/subdir.txt");
    }

    private static void assertFileContent(File outDir, File expDir, String file) throws IOException {
        assertTrue(IOUtils.contentEquals(new FileInputStream(new File(outDir, file)), new FileInputStream(new File(expDir, file))));
    }


}
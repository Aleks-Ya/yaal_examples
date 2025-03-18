package tar;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import util.FileUtil;
import util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static tar.Helper.assertFileContent;
import static util.ResourceUtil.resourceToFile;

/**
 * Unpack TAR archive compressed by GZIP ("*.tar.gz", "*.tgz").
 */
class UnpackTarGz {

    @Test
    void unpack() throws IOException {
        var outDir = FileUtil.createTempDirectoryFile(getClass().getSimpleName());
        System.out.println("outDir: " + outDir.getAbsolutePath());
        var is = ResourceUtil.resourceToInputStream(getClass(), "HDFS_CLIENT-configs.tar.gz");
        try (var gis = new GzipCompressorInputStream(is);
             var ais = new TarArchiveInputStream(gis)) {
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

        var expDir = resourceToFile(getClass(), "HDFS_CLIENT-configs.extracted/core-site.xml")
                .getParentFile();
        assertFileContent(outDir, expDir, "core-site.xml");
        assertFileContent(outDir, expDir, "hadoop-env.sh");
        assertFileContent(outDir, expDir, "hdfs-site.xml");
        assertFileContent(outDir, expDir, "log4j.properties");
    }

}
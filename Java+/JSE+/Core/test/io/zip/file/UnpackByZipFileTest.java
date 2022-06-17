package io.zip.file;

import io.zip.ZipHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

class UnpackByZipFileTest {
    @Test
    void unpack() throws IOException {
        try (var zf = new ZipFile(resourceToFile(UnpackByZipFileTest.class, "UnpackByZipFile.zip"))) {
            var entries = zf.entries();
            assertThat(Collections.list(entries)).hasSize(2);
            ZipHelper.assertZipEntry(zf, "a.txt", "aaa", 3);
            ZipHelper.assertZipEntry(zf, "b.txt", "bbb", 3);
        }
    }

}
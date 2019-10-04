package io.zip.file;

import io.zip.ZipHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static util.ResourceUtil.resourceToFile;

public class UnpackByZipFileTest {

    @Test
    public void unpack() throws IOException {
        try (ZipFile zf = new ZipFile(resourceToFile(UnpackByZipFileTest.class, "UnpackByZipFile.zip"))) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            assertThat(Collections.list(entries), hasSize(2));
            ZipHelper.assertZipEntry(zf, "a.txt", "aaa", 3);
            ZipHelper.assertZipEntry(zf, "b.txt", "bbb", 3);
        }
    }

}
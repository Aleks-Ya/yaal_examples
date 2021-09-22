package io.zip.stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static io.zip.ZipHelper.assertZipEntryEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;

class PackInFileTest {
    private static final String ROOT_ENTRY_NAME = "root.txt";
    private static final String ROOT_ENTRY_CONTENT = "root";

    private static final String FILE_1_ENTRY_NAME = "dir" + File.separator + "file1.txt";
    private static final String FILE_1_ENTRY_CONTENT = "abc";

    private static final String FILE_2_ENTRY_NAME = "dir" + File.separator + "file2.txt";
    private static final String FILE_2_ENTRY_CONTENT = "dbe";

    @Test
    void pack() throws IOException {
        var tempFile = File.createTempFile(getClass().getSimpleName(), ".zip");
        writeZipFile(tempFile);
        assertZipFile(tempFile);
    }

    private static void writeZipFile(File tempFile) throws IOException {
        System.out.println("File: " + tempFile.getAbsolutePath());
        try (var zos = new ZipOutputStream(new FileOutputStream(tempFile))) {
            writeEntry(zos, ROOT_ENTRY_NAME, ROOT_ENTRY_CONTENT);
            writeEntry(zos, FILE_1_ENTRY_NAME, FILE_1_ENTRY_CONTENT);
            writeEntry(zos, FILE_2_ENTRY_NAME, FILE_2_ENTRY_CONTENT);
        }

    }

    private static void assertZipFile(File tempFile) throws IOException {
        try (var zis = new ZipInputStream(new FileInputStream(tempFile))) {
            Map<String, String> contentMap = new HashMap<>();
            Map<String, ZipEntry> entryMap = new HashMap<>();
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                entryMap.put(entry.getName(), entry);
                var entryContent = new byte[5];
                var readBytes = IOUtils.read(zis, entryContent);
                contentMap.put(entry.getName(), new String(entryContent, 0, readBytes));
            }

            assertThat(entryMap, aMapWithSize(3));
            assertThat(contentMap, aMapWithSize(3));

            assertThat(contentMap.get(ROOT_ENTRY_NAME), equalTo(ROOT_ENTRY_CONTENT));

            var expRootEntry = new ZipEntry(ROOT_ENTRY_NAME);
            expRootEntry.setSize(ROOT_ENTRY_CONTENT.length());

            var actRootEntry = entryMap.get(ROOT_ENTRY_NAME);
            assertZipEntryEquals(actRootEntry, expRootEntry);
        }
    }

    private static void writeEntry(ZipOutputStream zos, String entryName, String entryContent) throws IOException {
        var bytes = entryContent.getBytes();
        var entry = new ZipEntry(entryName);
        zos.putNextEntry(entry);
        zos.write(bytes);
    }
}
package io.zip.stream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static io.zip.ZipHelper.assertZipEntryEquals;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PackInMemoryTest {
    private static final String ROOT_ENTRY_NAME = "root.txt";
    private static final String ROOT_ENTRY_CONTENT = "root";

    private static final String FILE_1_ENTRY_NAME = "dir" + File.separator + "file1.txt";
    private static final String FILE_1_ENTRY_CONTENT = "abc";

    private static final String FILE_2_ENTRY_NAME = "dir" + File.separator + "file2.txt";
    private static final String FILE_2_ENTRY_CONTENT = "dbe";

    @Test
    public void pack() throws IOException {
        byte[] bytes = writeZipFile();
        assertZipFile(bytes);
    }

    private static byte[] writeZipFile() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            writeEntry(zos, ROOT_ENTRY_NAME, ROOT_ENTRY_CONTENT);
            writeEntry(zos, FILE_1_ENTRY_NAME, FILE_1_ENTRY_CONTENT);
            writeEntry(zos, FILE_2_ENTRY_NAME, FILE_2_ENTRY_CONTENT);
        }
        return baos.toByteArray();
    }

    private static void assertZipFile(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ZipInputStream zis = new ZipInputStream(bais)) {
            Map<String, String> contentMap = new HashMap<>();
            Map<String, ZipEntry> entryMap = new HashMap<>();
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                entryMap.put(entry.getName(), entry);
                byte[] entryContent = new byte[5];
                int readBytes = IOUtils.read(zis, entryContent);
                contentMap.put(entry.getName(), new String(entryContent.clone(), 0, readBytes));
            }

            assertThat(entryMap, aMapWithSize(3));
            assertThat(contentMap, aMapWithSize(3));

            assertThat(contentMap.get(ROOT_ENTRY_NAME), equalTo(ROOT_ENTRY_CONTENT));

            ZipEntry expRootEntry = new ZipEntry(ROOT_ENTRY_NAME);
            expRootEntry.setSize(ROOT_ENTRY_CONTENT.length());

            ZipEntry actRootEntry = entryMap.get(ROOT_ENTRY_NAME);
            assertZipEntryEquals(actRootEntry, expRootEntry);
        }
    }

    private static void writeEntry(ZipOutputStream zos, String entryName, String entryContent) throws IOException {
        ZipEntry rootEntry = new ZipEntry(entryName);
        zos.putNextEntry(rootEntry);
        zos.write(entryContent.getBytes());
    }
}
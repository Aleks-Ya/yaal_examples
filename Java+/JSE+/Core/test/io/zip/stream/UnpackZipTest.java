package io.zip.stream;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;

public class UnpackZipTest {

    @Test
    public void unpack() throws IOException {
        try (InputStream is = ResourceUtil.resourceToInputStream(UnpackZipTest.class, "UnpackZip.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            Map<String, EntryData> dataList = new HashMap<>();
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                int size = (int) entry.getSize();
                byte[] entryContent = new byte[size];
                int readBytes = zis.read(entryContent, 0, size);
                assertThat(readBytes, equalTo(size));
                dataList.put(entry.getName(), new EntryData(entry, entryContent));
            }

            assertThat(dataList, aMapWithSize(2));
            assertEntry(dataList, "a.txt", "aaa");
            assertEntry(dataList, "b.txt", "bbb");
        }
    }

    private static void assertEntry(Map<String, EntryData> dataList, String entryName, String expContent) {
        EntryData data = dataList.get(entryName);
        assertThat(data.zipEntry.getName(), equalTo(entryName));
        assertThat(new String(data.content), equalTo(expContent));
        assertThat(data.content, equalTo(expContent.getBytes()));
    }

    private static class EntryData {
        ZipEntry zipEntry;
        byte[] content;

        public EntryData(ZipEntry zipEntry, byte[] content) {
            this.zipEntry = zipEntry;
            this.content = content;
        }
    }
}
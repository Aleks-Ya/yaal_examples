package io.zip.stream;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;

class UnpackZipTest {

    @Test
    void unpack() throws IOException {
        try (var is = ResourceUtil.resourceToInputStream(UnpackZipTest.class, "UnpackZip.zip");
             var zis = new ZipInputStream(is)) {
            Map<String, EntryData> dataList = new HashMap<>();
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                var size = (int) entry.getSize();
                var entryContent = new byte[size];
                var readBytes = zis.read(entryContent, 0, size);
                assertThat(readBytes, equalTo(size));
                dataList.put(entry.getName(), new EntryData(entry, entryContent));
            }

            assertThat(dataList, aMapWithSize(2));
            assertEntry(dataList, "a.txt", "aaa");
            assertEntry(dataList, "b.txt", "bbb");
        }
    }

    private static void assertEntry(Map<String, EntryData> dataList, String entryName, String expContent) {
        var data = dataList.get(entryName);
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
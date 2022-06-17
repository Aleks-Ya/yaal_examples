package io.zip.stream;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UnpackZipTest {

    private static void assertEntry(Map<String, EntryData> dataList, String entryName, String expContent) {
        var data = dataList.get(entryName);
        assertThat(data.zipEntry.getName()).isEqualTo(entryName);
        assertThat(new String(data.content)).isEqualTo(expContent);
        assertThat(data.content).isEqualTo(expContent.getBytes());
    }

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
                assertThat(readBytes).isEqualTo(size);
                dataList.put(entry.getName(), new EntryData(entry, entryContent));
            }

            assertThat(dataList).hasSize(2);
            assertEntry(dataList, "a.txt", "aaa");
            assertEntry(dataList, "b.txt", "bbb");
        }
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